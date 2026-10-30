package net.ona.mlpfrien.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.ona.mlpfrien.init.PonyBiomes;

public class WindingPathFeature extends Feature<NoneFeatureConfiguration> {
    private static final double SPACING = 160.0;
    private static final double WIDTH = 2.0;
    private boolean invert = false;

    public WindingPathFeature(boolean invert) {
        super(NoneFeatureConfiguration.CODEC);
        this.invert = invert;
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();

        ChunkPos chunk = new ChunkPos(context.origin());
        int startX = chunk.getMinBlockX();
        int startZ = chunk.getMinBlockZ();
        double phase = (level.getSeed() & 0xFFFFL) * 0.0001;
        boolean placed = false;

        for (int x = startX; x < startX + 16; x++) {
            for (int z = startZ; z < startZ + 16; z++) {

                double winding = 24.0 * Math.sin(z * 0.015 + (phase)) + 10.0 * Math.sin(z * 0.043 + (phase) * 1.37);

                double coordinate = x - z * 0.15 - winding;

                double nearest = Math.rint(coordinate / SPACING) * SPACING;
                double distance = Math.abs(coordinate - nearest);
                if (distance > WIDTH) {continue;}

                int y = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z) - 1;
                BlockPos ground = new BlockPos(x, y, z);

                if (!level.getBiome(ground).is(PonyBiomes.APPLE_FOREST)) {continue;}
                BlockState state = level.getBlockState(ground);

                if (!state.is(Blocks.GRASS_BLOCK) || !level.getBlockState(ground.above()).isAir()) {
                    continue;
                }

                BlockState pathBlock = context.random().nextFloat() < 0.2F ? Blocks.COARSE_DIRT.defaultBlockState() : Blocks.DIRT_PATH.defaultBlockState();
                BlockState pathBlockSecond = context.random().nextFloat() < 0.2F ? Blocks.MUD_BRICKS.defaultBlockState() : Blocks.PACKED_MUD.defaultBlockState();
                level.setBlock(ground, context.random().nextFloat() < 0.5F ? pathBlock : pathBlockSecond, 2);

                placed = true;
            }
        }
        return placed;
    }
}
