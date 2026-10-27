package net.ona.mlpfrien.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.init.PonyBlocks;

public class LBlockstateProvider extends BlockStateProvider {
    public LBlockstateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MyLittlePonyCraftingFriends.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        logBlock(PonyBlocks.GOLDEN_LOG.get());
        logBlock(PonyBlocks.STRIPPED_GOLDEN_LOG.get());
        axisBlock(PonyBlocks.GOLDEN_WOOD.get(),blockTexture(PonyBlocks.GOLDEN_LOG.get()));
        axisBlock(PonyBlocks.STRIPPED_GOLDEN_WOOD.get(),blockTexture(PonyBlocks.STRIPPED_GOLDEN_LOG.get()));
        simpleBlockWithItem(PonyBlocks.GOLDEN_LEAVES.get(), cubeAll(PonyBlocks.GOLDEN_LEAVES.get()));
        simpleBlockWithItem(PonyBlocks.GOLDEN_PLANKS.get(), cubeAll(PonyBlocks.GOLDEN_PLANKS.get()));
        slabBlock(PonyBlocks.GOLDEN_SLAB.get(), blockTexture(PonyBlocks.GOLDEN_PLANKS.get()),blockTexture(PonyBlocks.GOLDEN_PLANKS.get()));
        buttonBlock(PonyBlocks.GOLDEN_BUTTON.get(),blockTexture(PonyBlocks.GOLDEN_PLANKS.get()));
        stairsBlock(PonyBlocks.GOLDEN_STAIRS.get(),blockTexture(PonyBlocks.GOLDEN_PLANKS.get()));
        pressurePlateBlock(PonyBlocks.GOLDEN_PRESSURE_PlATE.get(),blockTexture(PonyBlocks.GOLDEN_PLANKS.get()));
        fenceBlock(PonyBlocks.GOLDEN_FENCE.get(),blockTexture(PonyBlocks.GOLDEN_PLANKS.get()));
        fenceGateBlock(PonyBlocks.GOLDEN_FENCE_GATE.get(),blockTexture(PonyBlocks.GOLDEN_PLANKS.get()));
        doorBlockWithRenderType(PonyBlocks.GOLDEN_DOOR.get(),modLoc("block/golden_door_bottom"),modLoc("block/golden_door_top"),mcLoc("cutout"));
        trapdoorBlockWithRenderType(PonyBlocks.GOLDEN_TRAPDOOR.get(),blockTexture(PonyBlocks.GOLDEN_TRAPDOOR.get()),false,mcLoc("cutout"));
        simpleBlock(PonyBlocks.GOLDEN_SAPLING.get(),models().cross(BuiltInRegistries.BLOCK.getKey(PonyBlocks.GOLDEN_SAPLING.get()).getPath(),
                blockTexture(PonyBlocks.GOLDEN_SAPLING.get())).renderType("cutout"));


        simpleBlockItem(PonyBlocks.GOLDEN_BUTTON.get(),models().buttonInventory("golden_button_inventory", blockTexture(PonyBlocks.GOLDEN_PLANKS.get())));
        simpleBlockItem(PonyBlocks.GOLDEN_FENCE.get(),models().fenceInventory("golden_fence_inventory", blockTexture(PonyBlocks.GOLDEN_PLANKS.get())));
        simpleBlockItem(PonyBlocks.GOLDEN_TRAPDOOR.get(),new ModelFile.UncheckedModelFile(modLoc("block/golden_trapdoor_bottom")));
    }
}
