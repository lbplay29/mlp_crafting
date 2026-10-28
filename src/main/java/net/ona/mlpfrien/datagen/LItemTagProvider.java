package net.ona.mlpfrien.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.init.PonyItems;
import net.ona.mlpfrien.init.PonyTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class LItemTagProvider extends ItemTagsProvider {
    public LItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, MyLittlePonyCraftingFriends.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(PonyTags.APPLES)
                .add(PonyItems.GREEN_APPLE.get())
                .add(PonyItems.SOUR_APPLE.get())
                .add(PonyItems.ZAP_APPLE.get())
                .add(Items.APPLE);
        tag(PonyTags.HANDHELD)
                .addTag(ItemTags.PICKAXES)
                .addTag(ItemTags.SHOVELS)
                .addTag(ItemTags.SWORDS)
                .addTag(ItemTags.HOES)
                .add(Items.MACE)
                .add(Items.FISHING_ROD)
                .add(Items.CARROT_ON_A_STICK)
                .add(Items.WARPED_FUNGUS_ON_A_STICK)
                .addTag(ItemTags.AXES);
    }
}
