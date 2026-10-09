package net.ona.mlpfrien.mixin;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.ona.mlpfrien.ponyvisuals.render.PonyRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {
    @Unique
    private PonyRenderer mlpfrien$ponyRenderer;

    @Inject(method = "<init>",at = @At("TAIL"))
    private void initPonyRenderer(EntityRendererProvider.Context context, boolean useSlimModel, CallbackInfo ci) {
        this.mlpfrien$ponyRenderer = new PonyRenderer(context,1.0f);
    }

    @Inject(method = "render(Lnet/minecraft/client/player/AbstractClientPlayer;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At("HEAD"),cancellable = true)
    public void renderCancel(AbstractClientPlayer entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        this.mlpfrien$ponyRenderer.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        ci.cancel();
    }

    @Inject(method = "renderLeftHand",
            at = @At("HEAD"),cancellable = true)
    public void renderLeftHandCancel(PoseStack poseStack, MultiBufferSource buffer, int combinedLight, AbstractClientPlayer player, CallbackInfo ci) {
        this.mlpfrien$ponyRenderer.renderLeftHand(poseStack, buffer, combinedLight, player);
        ci.cancel();
    }

    @Inject(method = "renderRightHand",
            at = @At("HEAD"),cancellable = true)
    public void renderRightHandCancel(PoseStack poseStack, MultiBufferSource buffer, int combinedLight, AbstractClientPlayer player, CallbackInfo ci) {
        this.mlpfrien$ponyRenderer.renderRightHand(poseStack, buffer, combinedLight, player);
        ci.cancel();
    }
}
