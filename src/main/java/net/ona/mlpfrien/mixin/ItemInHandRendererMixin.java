package net.ona.mlpfrien.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.ona.mlpfrien.ponyvisuals.MagicAuraRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * ItemInHandRenderer.renderItem is used both by PonyItemInHand (third person) and by vanilla's
 * first-person hand rendering, so hooking here gives the aura in both views.
 */
@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {
    @Inject(method = "renderItem", at = @At("HEAD"))
    private void mlpfrien$renderMagicAura(LivingEntity entity, ItemStack stack, ItemDisplayContext displayContext, boolean leftHand,
                                          PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        MagicAuraRenderer.renderAura(entity, stack, displayContext, leftHand, poseStack, buffer);
    }
}
