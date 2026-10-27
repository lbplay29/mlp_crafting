package net.ona.mlpfrien.init;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

public class PonyLog extends RotatedPillarBlock {
    public PonyLog(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (context.getItemInHand().getItem() instanceof AxeItem) {
            if (state.getBlock().equals(PonyBlocks.GOLDEN_LOG.get())) {
                return PonyBlocks.STRIPPED_GOLDEN_LOG.get().defaultBlockState().setValue(AXIS,state.getValue(AXIS));
            }
            if (state.getBlock().equals(PonyBlocks.GOLDEN_WOOD.get())) {
                return PonyBlocks.STRIPPED_GOLDEN_WOOD.get().defaultBlockState().setValue(AXIS,state.getValue(AXIS));
            }
        }

        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }
}
