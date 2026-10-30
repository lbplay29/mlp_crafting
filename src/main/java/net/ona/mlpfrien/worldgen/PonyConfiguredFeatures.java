package net.ona.mlpfrien.worldgen;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.init.PonyBlocks;
import net.ona.mlpfrien.init.PonyFeatures;

import java.util.List;

public class PonyConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?,?>> GOLDEN_TREE = registerKey("golden_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> ZAP_TREE = registerKey("zap_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> APPLE_TREE = registerKey("apple_tree");
    public static final ResourceKey<ConfiguredFeature<?,?>> BUSH_LARGE = registerKey("bush_large");
    public static final ResourceKey<ConfiguredFeature<?,?>> APPLE_BUSH = registerKey("apple_bush");
    public static final ResourceKey<ConfiguredFeature<?,?>> PATH = registerKey("path_apple");
    public static final ResourceKey<ConfiguredFeature<?,?>> PATH_Z = registerKey("path_apple_z");
    public static final ResourceKey<ConfiguredFeature<?,?>> APPLE_GRASS = registerKey("apple_grass");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?,?>> context) {
        RuleTest deepslateReplaceable = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

//        List<OreConfiguration.TargetBlockState> encrustedDeepslateOres = List.of(
//                OreConfiguration.target(deepslateReplaceable, Blocks.ACACIA_HANGING_SIGN.defaultBlockState()));

        register(context, GOLDEN_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(PonyBlocks.GOLDEN_LOG.get()),
                new StraightTrunkPlacer(4,2,0),randomLeavesState(PonyBlocks.GOLDEN_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(2),ConstantInt.of(0),3),new TwoLayersFeatureSize(1,0,1)).build());
        register(context, ZAP_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(PonyBlocks.ZAP_LOG.get()),
                new StraightTrunkPlacer(4,2,0),randomLeavesState(PonyBlocks.ZAP_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(2),ConstantInt.of(0),3),new TwoLayersFeatureSize(1,0,1)).build());
        register(context, APPLE_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG),
                new StraightTrunkPlacer(5,2,0),randomLeavesState(PonyBlocks.APPLE_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(2),ConstantInt.of(0),3),new TwoLayersFeatureSize(1,0,1)).build());
        register(context, BUSH_LARGE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG),
                new StraightTrunkPlacer(1,0,0),BlockStateProvider.simple(Blocks.OAK_LEAVES),
                new BushFoliagePlacer(ConstantInt.of(2),ConstantInt.of(1),2),new TwoLayersFeatureSize(0,0,0)).build());
        register(context, APPLE_BUSH, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG),
                new StraightTrunkPlacer(1,0,0),randomLeavesState(PonyBlocks.APPLE_LEAVES.get()),
                new BushFoliagePlacer(ConstantInt.of(2),ConstantInt.of(1),2),new TwoLayersFeatureSize(0,0,0)).build());

        context.register(APPLE_GRASS,new ConfiguredFeature<>(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.SHORT_GRASS)), List.of(Blocks.GRASS_BLOCK), 64)));
        context.register(PATH,new ConfiguredFeature<>(PonyFeatures.WINDING_PATH_X.get(), NoneFeatureConfiguration.INSTANCE));
        context.register(PATH_Z,new ConfiguredFeature<>(PonyFeatures.WINDING_PATH_Z.get(), NoneFeatureConfiguration.INSTANCE));
    }

    public static ResourceKey<ConfiguredFeature<?,?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?,?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?,?>> key, F feature, FC configuredFeat) {
        context.register(key, new ConfiguredFeature<>(feature, configuredFeat));
    }

    private static BlockState getLeafAge(Block block, int value) {
        BlockState state = block.defaultBlockState();
        IntegerProperty property = state.getProperties().stream().filter(p -> p instanceof IntegerProperty && p.getName().equals("age"))
                .map(IntegerProperty.class::cast).findFirst().orElseThrow();
        return state.setValue(property, value);
    }
    private static BlockStateProvider randomLeavesState(Block leaf) {
        return new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(getLeafAge(leaf,0), 16).add(getLeafAge(leaf,1), 4)
                        .add(getLeafAge(leaf,2), 4).add(getLeafAge(leaf,3), 1).build());
    }
}
