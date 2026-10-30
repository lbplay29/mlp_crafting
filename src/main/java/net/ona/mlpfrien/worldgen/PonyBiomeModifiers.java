package net.ona.mlpfrien.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.init.PonyBiomes;

public class PonyBiomeModifiers {
    public static ResourceKey<BiomeModifier> APPLE_TREE_ADD = registerKey("apple_tree_add");
    public static ResourceKey<BiomeModifier> GOLDEN_TREE_ADD = registerKey("golden_tree_add");
    public static ResourceKey<BiomeModifier> ZAP_TREE_ADD = registerKey("zap_tree_add");
    public static ResourceKey<BiomeModifier> BUSH_BIG_ADD = registerKey("bush_big_add");
    public static ResourceKey<BiomeModifier> APPLE_BUSH_ADD = registerKey("apple_bush_add");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(APPLE_TREE_ADD, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(PonyBiomes.APPLE_FOREST)),
                HolderSet.direct(placedFeatures.getOrThrow(PonyPlacedFeatures.APPLE_TREE_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(GOLDEN_TREE_ADD, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(PonyBiomes.APPLE_FOREST)),
                HolderSet.direct(placedFeatures.getOrThrow(PonyPlacedFeatures.GOLDEN_TREE_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(ZAP_TREE_ADD, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(PonyBiomes.APPLE_FOREST)),
                HolderSet.direct(placedFeatures.getOrThrow(PonyPlacedFeatures.ZAP_TREE_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(BUSH_BIG_ADD, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(PonyBiomes.APPLE_FOREST)),
                HolderSet.direct(placedFeatures.getOrThrow(PonyPlacedFeatures.BUSH_BIG_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(APPLE_BUSH_ADD, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(PonyBiomes.APPLE_FOREST)),
                HolderSet.direct(placedFeatures.getOrThrow(PonyPlacedFeatures.APPLE_BUSH_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, name));
    }
}
