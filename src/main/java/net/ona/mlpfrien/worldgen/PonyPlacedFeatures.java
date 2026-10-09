package net.ona.mlpfrien.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class PonyPlacedFeatures {
    public static final ResourceKey<PlacedFeature> ENCRUSTED_DEEPSLATE_PLACED_KEY = registerKey("encrusted_deepslate_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeat = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, ENCRUSTED_DEEPSLATE_PLACED_KEY, configuredFeat.getOrThrow(PonyConfiguredFeatures.ENCRUSTED_DEEPSLATE_KEY),
                PonyOrePlacements.commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80),VerticalAnchor.absolute(-15))));

    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath("bedrocked",name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?,?>> configuredFeat,
                                 List<PlacementModifier> modifierList) {
        context.register(key, new PlacedFeature(configuredFeat,List.copyOf(modifierList)));
    }
}
