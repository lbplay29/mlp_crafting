package net.ona.mlpfrien.ponyvisuals;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.ona.mlpfrien.ponyvisuals.models.PonyModel;

/**
 * Draws the unicorn horn's HornOverlay part with the same glowing, pulsing, shader-pack-friendly
 * aura as held items (MagicAuraRenderer), in the pony's aura color.
 *
 * PonyModel.renderToBuffer skips HornOverlay, so it isn't also drawn with the normal skin.
 */
@OnlyIn(Dist.CLIENT)
public class HornAuraLayer<T extends AbstractClientPlayer, M extends PonyModel<T>> extends RenderLayer<T, M> {
    /** Horn aura opacity relative to the item aura. Raise it for a stronger horn glow. */
    private static final float HORN_ALPHA_MULTIPLIER = 1.0F;

    public HornAuraLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T player,
                       float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
                       float netHeadYaw, float headPitch) {
        M model = getParentModel();
        // setupAnim already decided these: horn only for unicorns, overlay only while using magic.
        if (!model.head.visible || !model.Horn.visible || !model.HornOverlay.visible) return;
        if (player.isInvisible()) return;

        int rgb = MagicAuraRenderer.auraRgb(player);
        if (rgb < 0) return;
        float alpha = Mth.clamp(MagicAuraRenderer.auraAlpha(player) * HORN_ALPHA_MULTIPLIER, 0.0F, 1.0F);
        int argb = ((int) (alpha * 255.0F) << 24) | rgb;

        // Walk down the same chain of parts the model uses: root -> head -> Horn -> HornOverlay.
        poseStack.pushPose();
        model.root.translateAndRotate(poseStack);
        model.head.translateAndRotate(poseStack);
        model.Horn.translateAndRotate(poseStack);
        model.HornOverlay.render(poseStack, buffer.getBuffer(MagicAuraRenderer.auraRenderType()),
                LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, argb);
        poseStack.popPose();
    }
}
