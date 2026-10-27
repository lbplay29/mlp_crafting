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
    protected LootItemCondition.Builder noSilkTouchNorShear = HAS_SHEARS.or(hasSilkTouch()).invert();
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
        dropSelf(PonyBlocks.GOLDEN_DOOR.get());
        dropSelf(PonyBlocks.GOLDEN_TRAPDOOR.get());
        add(PonyBlocks.GOLDEN_LEAVES.get(), block -> createGoldenLeavesDrops(PonyBlocks.GOLDEN_LEAVES.get(), PonyBlocks.GOLDEN_SAPLING.get(),
                0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F));
//        add(PonyBlocks.ENCRUSTED_DEEPSLATE.get(), block ->
//                mulipleOreDrop(PonyBlocks.ENCRUSTED_DEEPSLATE.get(), PonyBlocks.BEDROCK_DUST.get(), 1, 2));
    }

    protected LootTable.Builder mulipleOreDrop(Block block, Item item, float minDrop, float maxDrop) {
        HolderLookup.RegistryLookup<Enchantment> registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(item)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1,2)))
                .apply(ApplyBonusCount.addOreBonusCount(registryLookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    protected LootTable.Builder createGoldenLeavesDrops(Block oakLeavesBlock, Block saplingBlock, float... chances) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createLeavesDrops(oakLeavesBlock, saplingBlock, chances).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                .when(noSilkTouchNorShear).add(((LootPoolSingletonContainer.Builder)this.applyExplosionCondition(
                        oakLeavesBlock, LootItem.lootTableItem(Items.GOLDEN_APPLE))).when(BonusLevelTableCondition.bonusLevelFlatChance(registrylookup.getOrThrow(Enchantments.FORTUNE),
                        0.05f, 0.0625f, 0.083333336f, 0.1f))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return PonyBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
