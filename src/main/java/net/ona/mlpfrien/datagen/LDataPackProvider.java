package net.ona.mlpfrien.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.worldgen.PonyBiomeModifiers;
import net.ona.mlpfrien.worldgen.PonyConfiguredFeatures;
import net.ona.mlpfrien.worldgen.PonyPlacedFeatures;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class LDataPackProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, PonyConfiguredFeatures::bootstrap);
//            .add(Registries.PLACED_FEATURE, PonyPlacedFeatures::bootstrap)
//            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, PonyBiomeModifiers::bootstrap);

    public LDataPackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(MyLittlePonyCraftingFriends.MODID));
    }
}
