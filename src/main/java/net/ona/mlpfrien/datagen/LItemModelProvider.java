package net.ona.mlpfrien.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.init.PonyBlocks;
import net.ona.mlpfrien.init.PonyItems;

public class LItemModelProvider extends ItemModelProvider {
    public LItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MyLittlePonyCraftingFriends.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(PonyItems.HAY_BURGUER.get());
        basicItem(PonyItems.CARAMEL_APPLE.get());
//        basicItem(PonyItems.GREEN_APPLE.get());
//        basicItem(PonyItems.SOUR_APPLE.get());
        basicItem(PonyItems.ZAP_APPLE.get());
        basicItem(PonyItems.CRUSADER_EMBLEM.get());
        basicItem(PonyBlocks.GOLDEN_SAPLING.asItem());
        basicItem(PonyBlocks.GOLDEN_DOOR.asItem());
        simpleBlockItem(PonyBlocks.GOLDEN_FENCE_GATE.get());
        simpleBlockItem(PonyBlocks.GOLDEN_SLAB.get());
        simpleBlockItem(PonyBlocks.GOLDEN_LEAVES.get());
        simpleBlockItem(PonyBlocks.GOLDEN_STAIRS.get());
        simpleBlockItem(PonyBlocks.GOLDEN_PRESSURE_PlATE.get());
        simpleBlockItem(PonyBlocks.GOLDEN_LOG.get());
        simpleBlockItem(PonyBlocks.STRIPPED_GOLDEN_LOG.get());
        simpleBlockItem(PonyBlocks.GOLDEN_WOOD.get());
        simpleBlockItem(PonyBlocks.STRIPPED_GOLDEN_WOOD.get());

        basicItem(PonyBlocks.ZAP_SAPLING.asItem());
        basicItem(PonyBlocks.ZAP_DOOR.asItem());
        simpleBlockItem(PonyBlocks.ZAP_FENCE_GATE.get());
        simpleBlockItem(PonyBlocks.ZAP_SLAB.get());
        simpleBlockItem(PonyBlocks.ZAP_LEAVES.get());
        simpleBlockItem(PonyBlocks.ZAP_STAIRS.get());
        simpleBlockItem(PonyBlocks.ZAP_PRESSURE_PlATE.get());
        simpleBlockItem(PonyBlocks.ZAP_LOG.get());
        simpleBlockItem(PonyBlocks.STRIPPED_ZAP_LOG.get());
        simpleBlockItem(PonyBlocks.ZAP_WOOD.get());
        simpleBlockItem(PonyBlocks.STRIPPED_ZAP_WOOD.get());
        simpleBlockItem(PonyBlocks.ZAP_LEAVES.get());

        basicItem(PonyBlocks.APPLE_SAPLING.asItem());
        simpleBlockItem(PonyBlocks.APPLE_LEAVES.get());
    }

    private void heldItem(DeferredItem<?> item) {
        this.withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath("mlpfrien","item/"+item.getId().getPath()));
    }
}
