package net.ona.mlpfrien.init;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.worldgen.PonyPlacedFeatures;

public class PonyBiomes extends OverworldBiomes{
    public static final ResourceKey<Biome> APPLE_FOREST = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, "apple_forest"));


    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);

        context.register(APPLE_FOREST, appleForest(placedFeatures, carvers));
    }
    private static void globalOverworldGeneration(BiomeGenerationSettings.Builder generationSettings) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultCrystalFormations(generationSettings);
        BiomeDefaultFeatures.addDefaultMonsterRoom(generationSettings);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generationSettings);
        BiomeDefaultFeatures.addDefaultSprings(generationSettings);
        BiomeDefaultFeatures.addSurfaceFreezing(generationSettings);
    }

    private static Biome appleForest(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(placedFeatures, carvers);
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().waterColor(0x3F76E4).waterFogColor(0x050533)
                .fogColor(0xC0D8FF).skyColor(OverworldBiomes.calculateSkyColor(0.4f)).grassColorOverride(0x20D040).foliageColorOverride(0x20D040)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build();

        globalOverworldGeneration(generation);
        BiomeDefaultFeatures.addDefaultOres(generation);
        BiomeDefaultFeatures.addDefaultSoftDisks(generation);
        BiomeDefaultFeatures.addDefaultFlowers(generation);
        BiomeDefaultFeatures.addDefaultGrass(generation);
        BiomeDefaultFeatures.addDefaultMushrooms(generation);
        generation.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS,placedFeatures.getOrThrow(PonyPlacedFeatures.PATH_KEY));
        generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,placedFeatures.getOrThrow(PonyPlacedFeatures.GRASS));

        BiomeDefaultFeatures.farmAnimals(spawns);
        BiomeDefaultFeatures.monsters(spawns,50,25,10,false);
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.BAT,5,1,3));

        return new Biome.BiomeBuilder().hasPrecipitation(true).temperature(0.4f).downfall(0.8F)
                .specialEffects(effects).mobSpawnSettings(spawns.build()).generationSettings(generation.build()).build();
    }
}
