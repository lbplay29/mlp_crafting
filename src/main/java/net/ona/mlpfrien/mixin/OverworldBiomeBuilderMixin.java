package net.ona.mlpfrien.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.neoforged.neoforge.data.loading.DatagenModLoader;
import net.ona.mlpfrien.init.PonyBiomes;
import net.ona.mlpfrien.worldgen.BiomeLookupContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(OverworldBiomeBuilder.class)
public abstract class OverworldBiomeBuilderMixin {
    @Inject(method = "pickMiddleBiome", at = @At("RETURN"), cancellable = true)
    private void mlpfrien$addAppleForest(int temperature, int humidity, Climate.Parameter param, CallbackInfoReturnable<ResourceKey<Biome>> cir) {
        if (BiomeLookupContext.isVanillaLookup()) {return;}
        if (DatagenModLoader.isRunningDataGen()) {return;}

        if (temperature == 2 && humidity == 2 && Biomes.FOREST.equals(cir.getReturnValue())) {
            cir.setReturnValue(PonyBiomes.APPLE_FOREST);
        }
    }
}
