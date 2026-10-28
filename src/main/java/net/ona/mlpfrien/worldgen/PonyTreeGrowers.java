package net.ona.mlpfrien.worldgen;

import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class PonyTreeGrowers {
    public static final TreeGrower GOLDEN_TREE = new TreeGrower("golden_tree", Optional.empty(),
            Optional.of(PonyConfiguredFeatures.GOLDEN_TREE),Optional.empty());
    public static final TreeGrower ZAP_TREE = new TreeGrower("zap_tree", Optional.empty(),
            Optional.of(PonyConfiguredFeatures.ZAP_TREE),Optional.empty());
    public static final TreeGrower APPLE_TREE = new TreeGrower("apple_tree", Optional.empty(),
            Optional.of(PonyConfiguredFeatures.APPLE_TREE),Optional.empty());
    public static final TreeGrower SOUR_APPLE_TREE = new TreeGrower("sour_apple_tree", Optional.empty(),
            Optional.of(PonyConfiguredFeatures.SOUR_APPLE_TREE),Optional.empty());
    public static final TreeGrower GREEN_APPLE_TREE = new TreeGrower("green_apple_tree", Optional.empty(),
            Optional.of(PonyConfiguredFeatures.GREEN_APPLE_TREE),Optional.empty());
}
