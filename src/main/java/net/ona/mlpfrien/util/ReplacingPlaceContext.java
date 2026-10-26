package net.ona.mlpfrien.util;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;

public class ReplacingPlaceContext extends BlockPlaceContext {
    public ReplacingPlaceContext(UseOnContext context) {
        super(context);
        this.replaceClicked = true;
    }
}
