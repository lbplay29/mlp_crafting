package net.ona.mlpfrien.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class PonyConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?,?>> ENCRUSTED_DEEPSLATE_KEY = registerKey("encrusted_deepslate");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?,?>> context) {
        RuleTest deepslateReplaceable = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> encrustedDeepslateOres = List.of(
                OreConfiguration.target(deepslateReplaceable, Blocks.ACACIA_HANGING_SIGN.defaultBlockState()));

        register(context, ENCRUSTED_DEEPSLATE_KEY, Feature.ORE, new OreConfiguration(encrustedDeepslateOres, 3));

    }

    public static ResourceKey<ConfiguredFeature<?,?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath("bedrocked",name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?,?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?,?>> key, F feature, FC configuredFeat) {
        context.register(key, new ConfiguredFeature<>(feature, configuredFeat));
    }
}
