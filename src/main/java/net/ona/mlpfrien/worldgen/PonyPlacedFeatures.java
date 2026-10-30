package net.ona.mlpfrien.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.init.PonyBlocks;

import java.util.List;
import java.util.stream.Stream;

public class PonyPlacedFeatures {
    public static final ResourceKey<PlacedFeature> APPLE_TREE_KEY = registerKey("apple_tree_placed");
    public static final ResourceKey<PlacedFeature> GOLDEN_TREE_KEY = registerKey("golden_tree_placed");
    public static final ResourceKey<PlacedFeature> ZAP_TREE_KEY = registerKey("zap_tree_placed");
    public static final ResourceKey<PlacedFeature> BUSH_BIG_KEY = registerKey("bush_big_placed");
    public static final ResourceKey<PlacedFeature> APPLE_BUSH_KEY = registerKey("apple_bush_placed");
    public static final ResourceKey<PlacedFeature> PATH_KEY = registerKey("path_apple_placed");
    public static final ResourceKey<PlacedFeature> PATH_KEY_Z = registerKey("path_apple_z_placed");
    public static final ResourceKey<PlacedFeature> GRASS = registerKey("apple_grass_key");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeat = context.lookup(Registries.CONFIGURED_FEATURE);
        List<PlacementModifier> basicTreeMult =VegetationPlacements.treePlacement(PlacementUtils.countExtra(1,0.5f,0), PonyBlocks.GOLDEN_SAPLING.get());

        register(context, APPLE_TREE_KEY, configuredFeat.getOrThrow(PonyConfiguredFeatures.APPLE_TREE), VegetationPlacements.treePlacement(PlacementUtils.countExtra(
                1,0.05f,1), PonyBlocks.APPLE_SAPLING.get()));
        register(context, GOLDEN_TREE_KEY, configuredFeat.getOrThrow(PonyConfiguredFeatures.GOLDEN_TREE), Stream.concat(basicTreeMult.stream(), Stream.of(
                RarityFilter.onAverageOnceEvery(500))).toList());
        register(context, ZAP_TREE_KEY, configuredFeat.getOrThrow(PonyConfiguredFeatures.ZAP_TREE), Stream.concat(VegetationPlacements.treePlacement(PlacementUtils
                .countExtra(1,0.5f,3), PonyBlocks.GOLDEN_SAPLING.get()).stream(), Stream.of(
                RarityFilter.onAverageOnceEvery(250))).toList());
        register(context, BUSH_BIG_KEY, configuredFeat.getOrThrow(PonyConfiguredFeatures.BUSH_LARGE), VegetationPlacements.treePlacement(PlacementUtils.countExtra(
                1,0.2f,1), PonyBlocks.APPLE_SAPLING.get()));
        register(context, APPLE_BUSH_KEY, configuredFeat.getOrThrow(PonyConfiguredFeatures.APPLE_BUSH), VegetationPlacements.treePlacement(PlacementUtils.countExtra(
                1,0.5f,0), PonyBlocks.APPLE_SAPLING.get()));
        register(context, PATH_KEY, configuredFeat.getOrThrow(PonyConfiguredFeatures.PATH), List.of());
        register(context, PATH_KEY_Z, configuredFeat.getOrThrow(PonyConfiguredFeatures.PATH_Z), List.of());
        register(context,GRASS,configuredFeat.getOrThrow(PonyConfiguredFeatures.APPLE_GRASS),VegetationPlacements.worldSurfaceSquaredWithCount(5));
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?,?>> configuredFeat,
                                 List<PlacementModifier> modifierList) {
        context.register(key, new PlacedFeature(configuredFeat,List.copyOf(modifierList)));
    }
}
