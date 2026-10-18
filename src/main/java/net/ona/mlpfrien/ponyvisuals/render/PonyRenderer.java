package net.ona.mlpfrien.ponyvisuals.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.numbers.StyledFormat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.ReadOnlyScoreInfo;
import net.minecraft.world.scores.Scoreboard;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.ona.mlpfrien.ponyvisuals.models.PonyModel;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@OnlyIn(Dist.CLIENT)
public class PonyRenderer extends LivingEntityRenderer<AbstractClientPlayer, PonyModel<AbstractClientPlayer>> {
    public PonyRenderer(EntityRendererProvider.Context context, float shadowRadius) {
        super(context, new PonyModel<>(context.bakeLayer(PonyModel.LAYER_LOCATION)), shadowRadius);
        this.addLayer(new PonyItemInHand<>(this,context.getItemInHandRenderer()));
    }
    public void render(AbstractClientPlayer entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(new RenderPonyEvent.Pre(entity, this, partialTicks, poseStack, buffer, packedLight)).isCanceled()) return;
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(new RenderPonyEvent.Post(entity, this, partialTicks, poseStack, buffer, packedLight) {
        });
    }

    public Vec3 getRenderOffset(AbstractClientPlayer entity, float partialTicks) {
        return new Vec3(0.0, (double)(entity.getScale() * -2.0F) / 16.0, 0.0);
    }

    public @NotNull ResourceLocation getTextureLocation(AbstractClientPlayer player) {
        try {
            return PonyTextureManager.getTexture(player);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void scale(AbstractClientPlayer livingEntity, PoseStack poseStack, float partialTickTime) {
        float f = 0.9375F;
        poseStack.scale(0.9375F, 0.9375F, 0.9375F);
    }

    protected void renderNameTag(AbstractClientPlayer entity, Component displayName, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float partialTick) {
        double d0 = this.entityRenderDispatcher.distanceToSqr(entity);
        poseStack.pushPose();
        if (d0 < 100.0) {
            Scoreboard scoreboard = entity.getScoreboard();
            Objective objective = scoreboard.getDisplayObjective(DisplaySlot.BELOW_NAME);
            if (objective != null) {
                ReadOnlyScoreInfo readonlyscoreinfo = scoreboard.getPlayerScoreInfo(entity, objective);
                Component component = ReadOnlyScoreInfo.safeFormatValue(readonlyscoreinfo, objective.numberFormatOrDefault(StyledFormat.NO_STYLE));
                super.renderNameTag(entity, Component.empty().append(component).append(CommonComponents.SPACE).append(objective.getDisplayName()),
                        poseStack, bufferSource, packedLight, partialTick);
                poseStack.translate(0.0F, 9.0F * 1.15F * 0.025F, 0.0F);
            }
        }
        super.renderNameTag(entity, displayName, poseStack, bufferSource, packedLight, partialTick);
        poseStack.popPose();
    }

    public void renderRightHand(PoseStack poseStack, MultiBufferSource buffer, int combinedLight, AbstractClientPlayer player) {
        if(!net.neoforged.neoforge.client.ClientHooks.renderSpecificFirstPersonArm(poseStack, buffer, combinedLight, player, HumanoidArm.RIGHT))
            this.renderHand(poseStack, buffer, combinedLight, player, this.model.rightArm, this.model.rightSleeve);
    }

    public void renderLeftHand(PoseStack poseStack, MultiBufferSource buffer, int combinedLight, AbstractClientPlayer player) {
        if(!net.neoforged.neoforge.client.ClientHooks.renderSpecificFirstPersonArm(poseStack, buffer, combinedLight, player, HumanoidArm.LEFT))
            this.renderHand(poseStack, buffer, combinedLight, player, this.model.leftArm, this.model.leftSleeve);
    }

    private void renderHand(PoseStack poseStack, MultiBufferSource buffer, int combinedLight, AbstractClientPlayer player, ModelPart rendererArm, ModelPart rendererArmwear) {
        PonyModel<AbstractClientPlayer> playermodel = this.getModel();
        playermodel.attackTime = 0.0F;
        playermodel.crouching = false;
        playermodel.swimAmount = 0.0F;
        playermodel.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        rendererArm.xRot = 0.0F;
        rendererArm.render(poseStack, buffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(player))), combinedLight, OverlayTexture.NO_OVERLAY);
        rendererArmwear.xRot = 0.0F;
        rendererArmwear.render(poseStack, buffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(player))), combinedLight, OverlayTexture.NO_OVERLAY);
    }

    protected void setupRotations(AbstractClientPlayer entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {
        float f = entity.getSwimAmount(partialTick);
        float f1 = entity.getViewXRot(partialTick);
        if (entity.isFallFlying()) {
            super.setupRotations(entity, poseStack, bob, yBodyRot, partialTick, scale);
            float f2 = (float)entity.getFallFlyingTicks() + partialTick;
            float f3 = Mth.clamp(f2 * f2 / 100.0F, 0.0F, 1.0F);
            if (!entity.isAutoSpinAttack()) {
                poseStack.mulPose(Axis.XP.rotationDegrees(f3 * (- f1)));
            }

            Vec3 vec3 = entity.getViewVector(partialTick);
            Vec3 vec31 = entity.getDeltaMovementLerped(partialTick);
            double d0 = vec31.horizontalDistanceSqr();
            double d1 = vec3.horizontalDistanceSqr();
            if (d0 > 0.0 && d1 > 0.0) {
                double d2 = (vec31.x * vec3.x + vec31.z * vec3.z) / Math.sqrt(d0 * d1);
                double d3 = vec31.x * vec3.z - vec31.z * vec3.x;
                poseStack.mulPose(Axis.YP.rotation((float)(Math.signum(d3) * Math.acos(d2))));
            }
        } else if (f > 0.0F) {
            super.setupRotations(entity, poseStack, bob, yBodyRot, partialTick, scale);
            float f4 = entity.isInWater() || entity.isInFluidType((fluidType, height) -> entity.canSwimInFluidType(fluidType)) ? -90.0F - entity.getXRot() : -90.0F;
            float f5 = Mth.lerp(f, 0.0F, f4);
            poseStack.mulPose(Axis.XP.rotationDegrees(f5+(90)));
            if (entity.isVisuallySwimming()) {
                poseStack.translate(0.0F, -1.0F, 0.3F);
            }
        } else {
            super.setupRotations(entity, poseStack, bob, yBodyRot, partialTick, scale);
        }
    }
}
