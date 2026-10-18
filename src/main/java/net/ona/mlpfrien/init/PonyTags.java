package net.ona.mlpfrien.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class PonyTags {
    public static TagKey<Item> APPLES = createItemTag("apples");
    public static TagKey<Item> HANDHELD = createItemTag("handheld");

    private static TagKey<Item> createItemTag(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("mlpfrien",name));
    }

    private static TagKey<Block> createBlockTag(String name) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("mlpfrien",name));
    }

    private PonyTags() {
    }
}
