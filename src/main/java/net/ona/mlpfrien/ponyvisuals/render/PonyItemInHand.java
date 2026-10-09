package net.ona.mlpfrien.ponyvisuals.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.data.PonyData;
import net.ona.mlpfrien.ponyvisuals.models.PonyModel;

@OnlyIn(Dist.CLIENT)
public class PonyItemInHand<T extends AbstractClientPlayer, M extends PonyModel<T>> extends RenderLayer<T, M> {
    private final ItemInHandRenderer itemInHandRenderer;

    public PonyItemInHand(RenderLayerParent<T, M> renderer, ItemInHandRenderer itemInHandRenderer) {
        super(renderer);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks,
            float ageInTicks, float netHeadYaw, float headPitch) {
        boolean flag = livingEntity.getMainArm() == HumanoidArm.RIGHT;
        ItemStack itemstack = flag ? livingEntity.getOffhandItem() : livingEntity.getMainHandItem();
        ItemStack itemstack1 = flag ? livingEntity.getMainHandItem() : livingEntity.getOffhandItem();
        if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {
            poseStack.pushPose();

            this.renderArmWithItem(livingEntity, itemstack1, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, poseStack, buffer, packedLight);
            this.renderArmWithItem(livingEntity, itemstack, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, poseStack, buffer, packedLight);
            poseStack.popPose();
        }
    }

    protected void renderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack,
            MultiBufferSource buffer, int packedLight) {
        if (!itemStack.isEmpty()) {
            poseStack.pushPose();
            PonyData data = livingEntity.getData(PonyAttachments.PONY_DATA);
            if (data.race()== PonyData.RACES.UNICORN) {
                getParentModel().root.translateAndRotate(poseStack);
                getParentModel().heldItemU.translateAndRotate(poseStack);
                poseStack.mulPose(Axis.ZP.rotationDegrees(180));
            } else {
                getParentModel().root.translateAndRotate(poseStack);
                getParentModel().rightArm.translateAndRotate(poseStack);
                //getParentModel().head.translateAndRotate(poseStack);
                //getParentModel().heldItem.translateAndRotate(poseStack);
                //poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                //poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                //poseStack.translate(-0.1,-0.1,0.5);
                poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
                poseStack.translate(-0.2,0.1,0.8);
            }
            boolean flag = arm == HumanoidArm.LEFT;
            poseStack.translate((float)(flag ? -1 : 1) / 4.0F, 0.125F, -0.625F);
            this.itemInHandRenderer.renderItem(livingEntity, itemStack, displayContext, flag, poseStack, buffer, packedLight);
            poseStack.popPose();
        }
    }
}

