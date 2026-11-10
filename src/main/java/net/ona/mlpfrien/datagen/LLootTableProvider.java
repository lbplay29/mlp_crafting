package net.ona.mlpfrien.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.init.PonyBlocks;
import net.ona.mlpfrien.init.PonyItems;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

public class LLootTableProvider implements LootTableSubProvider {
    public LLootTableProvider(HolderLookup.Provider provider) {}

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, "chests/barn_one")),
                LootTable.lootTable().withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2, 5))
                                .add(LootItem.lootTableItem(Items.HAY_BLOCK).setWeight(2)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
                                .add(LootItem.lootTableItem(Items.OAK_PLANKS).setWeight(10)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 6))))
                                .add(LootItem.lootTableItem(Items.STICK).setWeight(5)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                ).withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2, 5))
                        .add(LootItem.lootTableItem(Items.WATER_BUCKET).setWeight(2))
                        .add(LootItem.lootTableItem(PonyBlocks.APPLE_SAPLING).setWeight(10)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))))
                ));
        biConsumer.accept(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, "chests/barn_two")),
                LootTable.lootTable().withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1, 2))
                        .add(LootItem.lootTableItem(PonyBlocks.ZAP_SAPLING).setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
                        .add(LootItem.lootTableItem(PonyBlocks.ZAP_PLANKS).setWeight(10)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 6))))
                        .add(LootItem.lootTableItem(Items.STICK).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
                ).withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2, 5))
                        .add(LootItem.lootTableItem(Items.GLASS_BOTTLE).setWeight(8)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                        .add(LootItem.lootTableItem(PonyItems.ZAP_APPLE).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2))))
                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(10)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6))))
                ));
    }
}
