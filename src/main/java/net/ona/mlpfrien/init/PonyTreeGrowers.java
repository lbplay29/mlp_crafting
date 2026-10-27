package net.ona.mlpfrien.init;

import net.minecraft.world.level.block.grower.TreeGrower;
import net.ona.mlpfrien.worldgen.PonyConfiguredFeatures;

import java.util.Optional;

public class PonyTreeGrowers {
    public static final TreeGrower GOLDEN_TREE = new TreeGrower("golden_tree", Optional.empty(),
            Optional.of(PonyConfiguredFeatures.GOLDEN_TREE),Optional.empty());
}
