package net.ona.mlpfrien.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.ona.mlpfrien.init.PonyBlocks;

import java.util.Set;

public class LBlockLootTableProvider extends BlockLootSubProvider {
    protected LBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(PonyBlocks.GOLDEN_LOG.get());
        dropSelf(PonyBlocks.GOLDEN_SAPLING.get());
        dropSelf(PonyBlocks.STRIPPED_GOLDEN_LOG.get());
        dropSelf(PonyBlocks.GOLDEN_PLANKS.get());
        dropSelf(PonyBlocks.GOLDEN_FENCE_GATE.get());
        dropSelf(PonyBlocks.GOLDEN_FENCE.get());
        dropSelf(PonyBlocks.GOLDEN_PRESSURE_PlATE.get());
        dropSelf(PonyBlocks.GOLDEN_BUTTON.get());
        dropSelf(PonyBlocks.GOLDEN_STAIRS.get());
        dropSelf(PonyBlocks.GOLDEN_SLAB.get());
        dropSelf(PonyBlocks.GOLDEN_WOOD.get());
        dropSelf(PonyBlocks.STRIPPED_GOLDEN_WOOD.get());
        add(PonyBlocks.GOLDEN_DOOR.get(),block -> createDoorTable(PonyBlocks.GOLDEN_DOOR.get()));
        dropSelf(PonyBlocks.GOLDEN_TRAPDOOR.get());
        add(PonyBlocks.GOLDEN_LEAVES.get(), block -> createLeavesDrops(PonyBlocks.GOLDEN_LEAVES.get(), PonyBlocks.GOLDEN_SAPLING.get(),
                0.05F, 0.0625F, 0.083333336F, 0.1F));

        dropSelf(PonyBlocks.ZAP_LOG.get());
        dropSelf(PonyBlocks.ZAP_SAPLING.get());
        dropSelf(PonyBlocks.STRIPPED_ZAP_LOG.get());
        dropSelf(PonyBlocks.ZAP_PLANKS.get());
        dropSelf(PonyBlocks.ZAP_FENCE_GATE.get());
        dropSelf(PonyBlocks.ZAP_FENCE.get());
        dropSelf(PonyBlocks.ZAP_PRESSURE_PlATE.get());
        dropSelf(PonyBlocks.ZAP_BUTTON.get());
        dropSelf(PonyBlocks.ZAP_STAIRS.get());
        dropSelf(PonyBlocks.ZAP_SLAB.get());
        dropSelf(PonyBlocks.ZAP_WOOD.get());
        dropSelf(PonyBlocks.STRIPPED_ZAP_WOOD.get());
        add(PonyBlocks.ZAP_DOOR.get(),block -> createDoorTable(PonyBlocks.ZAP_DOOR.get()));
        dropSelf(PonyBlocks.ZAP_TRAPDOOR.get());
        add(PonyBlocks.ZAP_LEAVES.get(), block -> createLeavesDrops(PonyBlocks.ZAP_LEAVES.get(), PonyBlocks.ZAP_SAPLING.get(),
                0.05F, 0.0625F, 0.083333336F, 0.1F));

        add(PonyBlocks.APPLE_LEAVES.get(), block -> createLeavesDrops(PonyBlocks.APPLE_LEAVES.get(), PonyBlocks.APPLE_SAPLING.get(),
                0.05F, 0.0625F, 0.083333336F, 0.1F));
        dropSelf(PonyBlocks.APPLE_SAPLING.get());
    }

    protected LootTable.Builder mulipleOreDrop(Block block, Item item, float minDrop, float maxDrop) {
        HolderLookup.RegistryLookup<Enchantment> registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(item)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1,2)))
                .apply(ApplyBonusCount.addOreBonusCount(registryLookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return PonyBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
