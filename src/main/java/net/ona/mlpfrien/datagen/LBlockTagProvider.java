package net.ona.mlpfrien.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.internal.NeoForgeBlockTagsProvider;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.init.PonyBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class LBlockTagProvider extends BlockTagsProvider {
    public LBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MyLittlePonyCraftingFriends.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.LEAVES)
                .add(PonyBlocks.GOLDEN_LEAVES.get())
                .add(PonyBlocks.ZAP_LEAVES.get())
                .add(PonyBlocks.APPLE_LEAVES.get());
        tag(BlockTags.LOGS)
                .add(PonyBlocks.GOLDEN_LOG.get())
                .add(PonyBlocks.ZAP_LOG.get());
        tag(BlockTags.SAPLINGS)
                .add(PonyBlocks.GOLDEN_SAPLING.get())
                .add(PonyBlocks.ZAP_SAPLING.get());
        tag(BlockTags.FENCES)
                .add(PonyBlocks.GOLDEN_FENCE.get())
                .add(PonyBlocks.ZAP_FENCE.get());
        tag(BlockTags.WOODEN_FENCES)
                .add(PonyBlocks.GOLDEN_FENCE.get())
                .add(PonyBlocks.ZAP_FENCE.get());
        tag(BlockTags.FENCE_GATES)
                .add(PonyBlocks.GOLDEN_FENCE_GATE.get())
                .add(PonyBlocks.ZAP_FENCE_GATE.get());
        tag(BlockTags.TRAPDOORS)
                .add(PonyBlocks.GOLDEN_TRAPDOOR.get())
                .add(PonyBlocks.ZAP_TRAPDOOR.get());
        tag(BlockTags.WOODEN_TRAPDOORS)
                .add(PonyBlocks.GOLDEN_TRAPDOOR.get())
                .add(PonyBlocks.ZAP_TRAPDOOR.get());
        tag(BlockTags.DOORS)
                .add(PonyBlocks.GOLDEN_DOOR.get())
                .add(PonyBlocks.ZAP_DOOR.get());
        tag(BlockTags.WOODEN_DOORS)
                .add(PonyBlocks.GOLDEN_DOOR.get())
                .add(PonyBlocks.ZAP_DOOR.get());
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(PonyBlocks.GOLDEN_LOG.get())
                .add(PonyBlocks.STRIPPED_GOLDEN_LOG.get())
                .add(PonyBlocks.GOLDEN_PLANKS.get())
                .add(PonyBlocks.GOLDEN_WOOD.get())
                .add(PonyBlocks.STRIPPED_GOLDEN_WOOD.get())
                .add(PonyBlocks.GOLDEN_SLAB.get())
                .add(PonyBlocks.GOLDEN_STAIRS.get())
                .add(PonyBlocks.GOLDEN_FENCE.get())
                .add(PonyBlocks.GOLDEN_FENCE_GATE.get())
                .add(PonyBlocks.GOLDEN_BUTTON.get())
                .add(PonyBlocks.GOLDEN_PRESSURE_PlATE.get())
                .add(PonyBlocks.ZAP_LOG.get())
                .add(PonyBlocks.STRIPPED_ZAP_LOG.get())
                .add(PonyBlocks.ZAP_PLANKS.get())
                .add(PonyBlocks.ZAP_WOOD.get())
                .add(PonyBlocks.STRIPPED_ZAP_WOOD.get())
                .add(PonyBlocks.ZAP_SLAB.get())
                .add(PonyBlocks.ZAP_STAIRS.get())
                .add(PonyBlocks.ZAP_FENCE.get())
                .add(PonyBlocks.ZAP_FENCE_GATE.get())
                .add(PonyBlocks.ZAP_BUTTON.get())
                .add(PonyBlocks.ZAP_PRESSURE_PlATE.get());
        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(PonyBlocks.GOLDEN_LEAVES.get())
                .add(PonyBlocks.ZAP_LEAVES.get())
                .add(PonyBlocks.APPLE_LEAVES.get());
    }
}
