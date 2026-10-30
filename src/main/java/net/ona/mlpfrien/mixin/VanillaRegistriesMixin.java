package net.ona.mlpfrien.mixin;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.registries.VanillaRegistries;
import net.ona.mlpfrien.worldgen.BiomeLookupContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VanillaRegistries.class)
public abstract class VanillaRegistriesMixin {
    @Inject(method = "createLookup", at = @At("HEAD"))
    private static void mlpfrien$beginLookup(CallbackInfoReturnable<HolderLookup.Provider> cir) {
        BiomeLookupContext.beginVanillaLookup();
    }

    @Inject(method = "createLookup", at = @At("RETURN"))
    private static void mlpfrien$endLookup(CallbackInfoReturnable<HolderLookup.Provider> cir) {
        BiomeLookupContext.endVanillaLookup();
    }
}
