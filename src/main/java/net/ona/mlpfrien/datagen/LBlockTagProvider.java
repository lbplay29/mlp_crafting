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
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class LBlockTagProvider extends BlockTagsProvider {
    public LBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MyLittlePonyCraftingFriends.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        //tag(BlockTags.MINEABLE_WITH_PICKAXE)
        //        .add(Blocks.REINFORCED_DEEPSLATE)
       //         .add(Blocks.BEDROCK)
        //        .add(Blocks.END_PORTAL_FRAME);
    }
}
