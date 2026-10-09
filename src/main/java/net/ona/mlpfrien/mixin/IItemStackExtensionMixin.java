package net.ona.mlpfrien.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.data.PonyData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = IItemStackExtension.class, remap = false)
public interface IItemStackExtensionMixin {
    @Inject(method = "canElytraFly", at = @At("RETURN"), cancellable = true)
    private void pegasusCanElytraFly(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && mlpfrien$isPegasus(entity)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "elytraFlightTick", at = @At("RETURN"), cancellable = true)
    private void pegasusContinueElytraFlight(LivingEntity entity, int flightTicks, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && mlpfrien$isPegasus(entity)) {
            cir.setReturnValue(true);
        }
    }

    @Unique
    private static boolean mlpfrien$isPegasus(LivingEntity entity) {
        if (!(entity instanceof Player player)) {
            return false;
        }
        return player.getData(PonyAttachments.PONY_DATA).race() == PonyData.RACES.PEGASUS;
    }
}