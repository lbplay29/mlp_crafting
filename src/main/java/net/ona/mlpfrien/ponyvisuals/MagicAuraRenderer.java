package net.ona.mlpfrien.ponyvisuals;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.PonyAttachments;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Draws a translucent, glowing aura around a held item, tinted with the holder's magic color
 * (AppearanceData.colors().get(3)).
 *
 * Only vanilla render types are used (the same one the Warden's glowing parts use), so it keeps
 * working with Iris/Oculus shader packs.
 *
 * Flat items (item/generated, item/handheld): the aura is built like vanilla builds a flat item's
 * 3D model. Every transparent pixel within GLOW_RADIUS of an opaque pixel becomes a small box with
 * front, back and side faces, a bit thicker than the item. That gives a real 3D outline, holes
 * included (barrier, etc). The shape is computed once per texture and cached.
 *
 * Block / 3D models: a slightly bigger copy of the model, drawn in the aura color.
 *
 * Called from ItemInHandRendererMixin right before the real item is drawn, so it works for
 * both third person (PonyItemInHand) and first person.
 */
@OnlyIn(Dist.CLIENT)
public final class MagicAuraRenderer {
    /** Which entry of AppearanceData.colors() is the aura color. */
    public static final int AURA_COLOR_INDEX = 3;
    /** Set to true to only show the aura for races that can use magic (unicorns). */
    public static final boolean UNICORNS_ONLY = true;

    /** Flat items: glow thickness around the item's edges, in texture pixels. */
    private static final int GLOW_RADIUS = 1;
    /** Flat items: how far the aura sticks out in front of / behind the item, in pixels (item is 1px thick). */
    private static final float EXTRA_DEPTH_PIXELS = 0.5F;
    /** Block items: aura size relative to the model. */
    private static final float BLOCK_SHELL_SCALE = 1.12F;
    private static final float BLOCK_SHELL_PULSE = 0.03F;

    /**
     * Aura opacity. Note the vanilla shader drops anything under 0.1 alpha, and you see the front
     * and back faces through each other, so keep these fairly low.
     */
    private static final float BASE_ALPHA = 0.40F;
    private static final float PULSE_ALPHA = 0.20F;
    private static final float PULSE_SPEED = 0.15F;

    /** Plain white texture; the vertex color does the tinting. */
    private static final ResourceLocation WHITE =
            ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, "textures/misc/aura_white.png");

    /**
     * Cached aura shapes, keyed by the item's first texture. Weak keys: after a resource reload the
     * old sprites are thrown away and their cached shapes go with them.
     */
    private static final Map<TextureAtlasSprite, AuraMesh> MESH_CACHE = new WeakHashMap<>();

    private MagicAuraRenderer() {}

    // ---- Shared helpers (also used by HornAuraLayer) ----

    /** The glowing translucent render type every aura uses. Vanilla, so shader packs handle it. */
    public static RenderType auraRenderType() {
        return RenderType.entityTranslucentEmissive(WHITE);
    }

    /** The player's aura color as 0xRRGGBB, or -1 if their appearance data has no aura color. */
    public static int auraRgb(Player player) {
        List<Integer> colors = player.getData(PonyAttachments.APPEARANCE_DATA).colors();
        if (colors.size() <= AURA_COLOR_INDEX) return -1;
        return colors.get(AURA_COLOR_INDEX) & 0xFFFFFF;
    }

    /** 0..1 pulse value, so every aura on the same pony pulses in sync. */
    public static float pulse(LivingEntity entity) {
        float time = entity.tickCount + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
        return Mth.sin(time * PULSE_SPEED) * 0.5F + 0.5F;
    }

    /** Current aura opacity (0..1) including the pulse. */
    public static float auraAlpha(LivingEntity entity) {
        return BASE_ALPHA + PULSE_ALPHA * pulse(entity);
    }

    // ---- Held item aura ----

    public static void renderAura(LivingEntity entity, ItemStack stack, ItemDisplayContext displayContext,
                                  boolean leftHand, PoseStack poseStack, MultiBufferSource buffer) {
        if (stack.isEmpty() || !(entity instanceof Player player)) return;
        if (UNICORNS_ONLY && !player.getData(PonyAttachments.PONY_DATA).race().canUseMagic()) return;

        int rgb = auraRgb(player);
        if (rgb < 0) return;

        Minecraft mc = Minecraft.getInstance();
        // Same model lookup vanilla uses (resolves overrides like bow pulling, compass, etc.)
        BakedModel model = mc.getItemRenderer().getModel(stack, entity.level(), entity, entity.getId() + displayContext.ordinal());
        // Shields, tridents, banners, chests... are drawn by code, not quads. Nothing to outline.
        if (model.isCustomRenderer()) return;

        float pulse = pulse(entity);
        float alpha = BASE_ALPHA + PULSE_ALPHA * pulse;
        float r = ((rgb >> 16) & 0xFF) / 255.0F;
        float g = ((rgb >> 8) & 0xFF) / 255.0F;
        float b = (rgb & 0xFF) / 255.0F;

        poseStack.pushPose();
        // Same as ItemRenderer.render: apply the model's display transform (this may swap the model),
        // then shift from "centered" to model space (0..1).
        model = model.applyTransform(displayContext, poseStack, leftHand);
        List<BakedQuad> quads = collectQuads(model, stack);
        VertexConsumer consumer = buffer.getBuffer(auraRenderType());

        if (!model.isGui3d()) {
            // Flat item (generated / handheld).
            poseStack.translate(-0.5F, -0.5F, -0.5F);
            AuraMesh mesh = getMesh(quads);
            if (mesh != null) {
                mesh.render(consumer, poseStack.last(), r, g, b, alpha);
            }
        } else {
            // Block-like model: slightly bigger copy, grown around the model's center.
            float scale = BLOCK_SHELL_SCALE + BLOCK_SHELL_PULSE * pulse;
            poseStack.scale(scale, scale, scale);
            poseStack.translate(-0.5F, -0.5F, -0.5F);
            PoseStack.Pose pose = poseStack.last();
            for (BakedQuad quad : quads) {
                consumer.putBulkData(pose, quad, r, g, b, alpha * 0.6F, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
            }
        }
        poseStack.popPose();
    }

    private static List<BakedQuad> collectQuads(BakedModel model, ItemStack stack) {
        List<BakedQuad> quads = new ArrayList<>();
        RandomSource random = RandomSource.create();
        for (BakedModel pass : model.getRenderPasses(stack, true)) {
            for (Direction direction : Direction.values()) {
                random.setSeed(42L);
                quads.addAll(pass.getQuads(null, direction, random));
            }
            random.setSeed(42L);
            quads.addAll(pass.getQuads(null, null, random));
        }
        return quads;
    }

    private static AuraMesh getMesh(List<BakedQuad> quads) {
        // Every texture layer of the item (e.g. potion bottle + liquid, leather armor + overlay).
        Set<TextureAtlasSprite> spriteSet = new LinkedHashSet<>();
        for (BakedQuad quad : quads) {
            spriteSet.add(quad.getSprite());
        }
        if (spriteSet.isEmpty()) return null;
        List<TextureAtlasSprite> sprites = List.copyOf(spriteSet);

        AuraMesh mesh = MESH_CACHE.get(sprites.get(0));
        if (mesh == null || !mesh.sprites.equals(sprites)) {
            mesh = AuraMesh.build(sprites);
            MESH_CACHE.put(sprites.get(0), mesh);
        }
        return mesh;
    }

    /** One aura "pixel box" face: 4 corners + normal + how strong the glow is there. */
    private record Face(float[] corners, float nx, float ny, float nz, float fade) {}

    private static final class AuraMesh {
        final List<TextureAtlasSprite> sprites;
        final List<Face> faces;

        private AuraMesh(List<TextureAtlasSprite> sprites, List<Face> faces) {
            this.sprites = sprites;
            this.faces = faces;
        }

        static AuraMesh build(List<TextureAtlasSprite> sprites) {
            SpriteContents base = sprites.get(0).contents();
            int w = base.width();
            int h = base.height();

            // Merge all layers: a pixel is "item" if any layer is opaque there.
            boolean[][] opaque = new boolean[w][h];
            for (TextureAtlasSprite sprite : sprites) {
                SpriteContents contents = sprite.contents();
                if (contents.width() != w || contents.height() != h) continue;
                for (int x = 0; x < w; x++) {
                    for (int y = 0; y < h; y++) {
                        if (!contents.isTransparent(0, x, y)) opaque[x][y] = true;
                    }
                }
            }

            // Glow strength for every pixel in the texture plus a GLOW_RADIUS border around it.
            int R = GLOW_RADIUS;
            int gw = w + 2 * R;
            int gh = h + 2 * R;
            float[][] fade = new float[gw][gh];
            for (int gx = 0; gx < gw; gx++) {
                for (int gy = 0; gy < gh; gy++) {
                    int px = gx - R;
                    int py = gy - R;
                    if (isOpaque(opaque, w, h, px, py)) continue; // the item itself is here
                    float nearest = Float.MAX_VALUE;
                    for (int dx = -R; dx <= R; dx++) {
                        for (int dy = -R; dy <= R; dy++) {
                            if (isOpaque(opaque, w, h, px + dx, py + dy)) {
                                nearest = Math.min(nearest, Mth.sqrt(dx * dx + dy * dy));
                            }
                        }
                    }
                    if (nearest <= R + 0.5F) {
                        fade[gx][gy] = Mth.clamp(1.0F - (nearest - 1.0F) / (R + 1.0F), 0.0F, 1.0F);
                    }
                }
            }

            // Build boxes. Vanilla flat items span z = 7.5/16 .. 8.5/16 in model space.
            float sx = 1.0F / w;
            float sy = 1.0F / h;
            float z0 = (7.5F - EXTRA_DEPTH_PIXELS) / 16.0F;
            float z1 = (8.5F + EXTRA_DEPTH_PIXELS) / 16.0F;
            List<Face> faces = new ArrayList<>();
            for (int gx = 0; gx < gw; gx++) {
                for (int gy = 0; gy < gh; gy++) {
                    float f = fade[gx][gy];
                    if (f <= 0.0F) continue;
                    float x0 = (gx - R) * sx;
                    float x1 = x0 + sx;
                    // Texture y goes down, model y goes up.
                    float y1 = 1.0F - (gy - R) * sy;
                    float y0 = y1 - sy;

                    // Front and back.
                    faces.add(new Face(new float[]{x0, y0, z1, x1, y0, z1, x1, y1, z1, x0, y1, z1}, 0, 0, 1, f));
                    faces.add(new Face(new float[]{x0, y1, z0, x1, y1, z0, x1, y0, z0, x0, y0, z0}, 0, 0, -1, f));
                    // Side walls wherever the neighbor isn't aura.
                    if (!hasAura(fade, gw, gh, gx - 1, gy)) {
                        faces.add(new Face(new float[]{x0, y0, z0, x0, y0, z1, x0, y1, z1, x0, y1, z0}, -1, 0, 0, f));
                    }
                    if (!hasAura(fade, gw, gh, gx + 1, gy)) {
                        faces.add(new Face(new float[]{x1, y0, z1, x1, y0, z0, x1, y1, z0, x1, y1, z1}, 1, 0, 0, f));
                    }
                    if (!hasAura(fade, gw, gh, gx, gy - 1)) { // texture up = model +y
                        faces.add(new Face(new float[]{x0, y1, z1, x1, y1, z1, x1, y1, z0, x0, y1, z0}, 0, 1, 0, f));
                    }
                    if (!hasAura(fade, gw, gh, gx, gy + 1)) {
                        faces.add(new Face(new float[]{x0, y0, z0, x1, y0, z0, x1, y0, z1, x0, y0, z1}, 0, -1, 0, f));
                    }
                }
            }
            return new AuraMesh(sprites, faces);
        }

        private static boolean isOpaque(boolean[][] opaque, int w, int h, int x, int y) {
            return x >= 0 && y >= 0 && x < w && y < h && opaque[x][y];
        }

        private static boolean hasAura(float[][] fade, int gw, int gh, int x, int y) {
            return x >= 0 && y >= 0 && x < gw && y < gh && fade[x][y] > 0.0F;
        }

        private static final float[] U = {0, 1, 1, 0};
        private static final float[] V = {1, 1, 0, 0};

        void render(VertexConsumer consumer, PoseStack.Pose pose, float r, float g, float b, float alpha) {
            for (Face face : faces) {
                float a = alpha * face.fade();
                float[] c = face.corners();
                for (int i = 0; i < 4; i++) {
                    consumer.addVertex(pose, c[i * 3], c[i * 3 + 1], c[i * 3 + 2])
                            .setColor(r, g, b, a)
                            .setUv(U[i], V[i])
                            .setOverlay(OverlayTexture.NO_OVERLAY)
                            .setLight(LightTexture.FULL_BRIGHT)
                            .setNormal(pose, face.nx(), face.ny(), face.nz());
                }
            }
        }
    }
}
