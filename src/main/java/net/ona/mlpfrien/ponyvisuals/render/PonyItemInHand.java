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
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.data.PonyData;
import net.ona.mlpfrien.init.PonyTags;
import net.ona.mlpfrien.ponyvisuals.models.PonyModel;

@OnlyIn(Dist.CLIENT)
public class PonyItemInHand<T extends AbstractClientPlayer, M extends PonyModel<T>> extends RenderLayer<T, M> {
    private final ItemInHandRenderer itemInHandRenderer;

    public PonyItemInHand(RenderLayerParent<T, M> renderer, ItemInHandRenderer itemInHandRenderer) {
        super(renderer);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
            T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack mainHand = livingEntity.getMainHandItem();
        ItemStack offHand = livingEntity.getOffhandItem();

        if (!mainHand.isEmpty()) {
            renderMainHandItem(livingEntity, mainHand, poseStack, buffer, packedLight);
        }

        if (!offHand.isEmpty()) {
            HumanoidArm offArm = livingEntity.getMainArm() == HumanoidArm.RIGHT ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
            renderOffhandItem(livingEntity, offHand, offArm, poseStack, buffer, packedLight);
        }
    }

    private void renderOffhandItem(T livingEntity, ItemStack stack, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        getParentModel().root.translateAndRotate(poseStack);
        if (arm == HumanoidArm.LEFT) {
            getParentModel().leftArm.translateAndRotate(poseStack);
        } else {
            getParentModel().rightArm.translateAndRotate(poseStack);
        }
        poseStack.translate(0.0F, 0.1F, 0.125F);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        boolean leftHand = arm == HumanoidArm.LEFT;
        ItemDisplayContext displayContext = leftHand ? ItemDisplayContext.THIRD_PERSON_LEFT_HAND : ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;

        this.itemInHandRenderer.renderItem(livingEntity, stack, displayContext, leftHand, poseStack, buffer, packedLight);

        poseStack.popPose();
    }

    private void renderMainHandItem(T livingEntity, ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        PonyData data = livingEntity.getData(PonyAttachments.PONY_DATA);
        getParentModel().root.translateAndRotate(poseStack);

        if (data.race().canUseMagic()) {
            getParentModel().heldItemU.translateAndRotate(poseStack);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        } else {
            itemTypeMatch(stack, poseStack);
        }
        this.itemInHandRenderer.renderItem(livingEntity, stack, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, true, poseStack, buffer, packedLight);
        poseStack.popPose();
    }


    private void itemTypeMatch(ItemStack stack, PoseStack poseStack) {
        getParentModel().head.translateAndRotate(poseStack);
        getParentModel().heldItem.translateAndRotate(poseStack);
        if (stack.is(PonyTags.HANDHELD)) {
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
            poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            poseStack.translate(0,0.1,0);
        } else if (stack.getItem() instanceof BlockItem) {
            poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            //poseStack.translate(-0.2,-0.2,0.6);
        } else if (stack.getItem() instanceof PotionItem || stack.getItem() instanceof BottleItem){
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            poseStack.translate(0,-0.4,0);
        } else if (stack.getItem() instanceof TridentItem) {
            poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
            poseStack.mulPose(Axis.XP.rotationDegrees(-30.0F));
            poseStack.translate(0,0,-0.2);
        } else if (stack.getItem() instanceof CrossbowItem) {
            //poseStack.translate(-0.2,-0.2,0.5);
        } else if (stack.getItem() instanceof BowItem) {
            //poseStack.translate(-0.2,-0.2,0.5);
        } else {
            poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            poseStack.translate(0,-0.1,-0.1);
        }
    }
}

