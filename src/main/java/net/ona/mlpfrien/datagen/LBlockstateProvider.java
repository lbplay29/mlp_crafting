package net.ona.mlpfrien.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.block.AppleLeavesBlock;
import net.ona.mlpfrien.init.PonyBlocks;

import javax.annotation.Nullable;
import java.util.List;

public class LBlockstateProvider extends BlockStateProvider {
    private ExistingFileHelper existingFileHelper;

    public LBlockstateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MyLittlePonyCraftingFriends.MODID, exFileHelper);
        existingFileHelper = exFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        logBlock(PonyBlocks.GOLDEN_LOG.get());
        logBlock(PonyBlocks.STRIPPED_GOLDEN_LOG.get());
        axisBlock(PonyBlocks.GOLDEN_WOOD.get(),blockTexture(PonyBlocks.GOLDEN_LOG.get()));
        axisBlock(PonyBlocks.STRIPPED_GOLDEN_WOOD.get(),blockTexture(PonyBlocks.STRIPPED_GOLDEN_LOG.get()));
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

        logBlock(PonyBlocks.ZAP_LOG.get());
        logBlock(PonyBlocks.STRIPPED_ZAP_LOG.get());
        axisBlock(PonyBlocks.ZAP_WOOD.get(),blockTexture(PonyBlocks.ZAP_LOG.get()));
        axisBlock(PonyBlocks.STRIPPED_ZAP_WOOD.get(),blockTexture(PonyBlocks.STRIPPED_ZAP_LOG.get()));
        simpleBlockWithItem(PonyBlocks.ZAP_PLANKS.get(), cubeAll(PonyBlocks.ZAP_PLANKS.get()));
        slabBlock(PonyBlocks.ZAP_SLAB.get(), blockTexture(PonyBlocks.ZAP_PLANKS.get()),blockTexture(PonyBlocks.ZAP_PLANKS.get()));
        buttonBlock(PonyBlocks.ZAP_BUTTON.get(),blockTexture(PonyBlocks.ZAP_PLANKS.get()));
        stairsBlock(PonyBlocks.ZAP_STAIRS.get(),blockTexture(PonyBlocks.ZAP_PLANKS.get()));
        pressurePlateBlock(PonyBlocks.ZAP_PRESSURE_PlATE.get(),blockTexture(PonyBlocks.ZAP_PLANKS.get()));
        fenceBlock(PonyBlocks.ZAP_FENCE.get(),blockTexture(PonyBlocks.ZAP_PLANKS.get()));
        fenceGateBlock(PonyBlocks.ZAP_FENCE_GATE.get(),blockTexture(PonyBlocks.ZAP_PLANKS.get()));
        doorBlockWithRenderType(PonyBlocks.ZAP_DOOR.get(),modLoc("block/zap_door_bottom"),modLoc("block/zap_door_top"),mcLoc("cutout"));
        trapdoorBlockWithRenderType(PonyBlocks.ZAP_TRAPDOOR.get(),blockTexture(PonyBlocks.ZAP_TRAPDOOR.get()),false,mcLoc("cutout"));
        simpleBlock(PonyBlocks.ZAP_SAPLING.get(),models().cross(BuiltInRegistries.BLOCK.getKey(PonyBlocks.ZAP_SAPLING.get()).getPath(),
                blockTexture(PonyBlocks.ZAP_SAPLING.get())).renderType("cutout"));
        simpleBlockItem(PonyBlocks.ZAP_BUTTON.get(),models().buttonInventory("zap_button_inventory", blockTexture(PonyBlocks.ZAP_PLANKS.get())));
        simpleBlockItem(PonyBlocks.ZAP_FENCE.get(),models().fenceInventory("zap_fence_inventory", blockTexture(PonyBlocks.ZAP_PLANKS.get())));
        simpleBlockItem(PonyBlocks.ZAP_TRAPDOOR.get(),new ModelFile.UncheckedModelFile(modLoc("block/zap_trapdoor_bottom")));

        simpleBlock(PonyBlocks.APPLE_SAPLING.get(),models().cross(BuiltInRegistries.BLOCK.getKey(PonyBlocks.APPLE_SAPLING.get()).getPath(),
                blockTexture(PonyBlocks.APPLE_SAPLING.get())).renderType("cutout"));

        getVariantBuilder(PonyBlocks.ZAP_LEAVES.get())
                .partialState().with(AppleLeavesBlock.AGE, 0).modelForState()
                .modelFile(appleVariantTextures(PonyBlocks.ZAP_LEAVES.get()).get(0)).addModel()
                .partialState().with(AppleLeavesBlock.AGE, 1).modelForState()
                .modelFile(appleVariantTextures(PonyBlocks.ZAP_LEAVES.get()).get(1)).addModel()
                .partialState().with(AppleLeavesBlock.AGE, 2).modelForState()
                .modelFile(appleVariantTextures(PonyBlocks.ZAP_LEAVES.get()).get(2)).addModel()
                .partialState().with(AppleLeavesBlock.AGE, 3).modelForState()
                .modelFile(appleVariantTextures(PonyBlocks.ZAP_LEAVES.get()).get(3)).addModel();
        getVariantBuilder(PonyBlocks.GOLDEN_LEAVES.get())
                .partialState().with(AppleLeavesBlock.AGE, 0).modelForState()
                .modelFile(appleVariantTextures(PonyBlocks.GOLDEN_LEAVES.get()).get(0)).addModel()
                .partialState().with(AppleLeavesBlock.AGE, 1).modelForState()
                .modelFile(appleVariantTextures(PonyBlocks.GOLDEN_LEAVES.get()).get(1)).addModel()
                .partialState().with(AppleLeavesBlock.AGE, 2).modelForState()
                .modelFile(appleVariantTextures(PonyBlocks.GOLDEN_LEAVES.get()).get(2)).addModel()
                .partialState().with(AppleLeavesBlock.AGE, 3).modelForState()
                .modelFile(appleVariantTextures(PonyBlocks.GOLDEN_LEAVES.get()).get(3)).addModel();
        getVariantBuilder(PonyBlocks.APPLE_LEAVES.get())
                .partialState().with(AppleLeavesBlock.AGE, 0).modelForState()
                .modelFile(appleVariantTextures(PonyBlocks.APPLE_LEAVES.get()).get(0)).addModel()
                .partialState().with(AppleLeavesBlock.AGE, 1).modelForState()
                .modelFile(appleVariantTextures(PonyBlocks.APPLE_LEAVES.get()).get(1)).addModel()
                .partialState().with(AppleLeavesBlock.AGE, 2).modelForState()
                .modelFile(appleVariantTextures(PonyBlocks.APPLE_LEAVES.get()).get(2)).addModel()
                .partialState().with(AppleLeavesBlock.AGE, 3).modelForState()
                .modelFile(appleVariantTextures(PonyBlocks.APPLE_LEAVES.get()).get(3)).addModel();
    }

    private List<ModelFile> appleVariantTextures(Block block) {
        String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
        ResourceLocation buddingLoc = modLoc("block/"+name+"_budding_overlay");
        boolean hasBudding = existingFileHelper.exists(buddingLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

        ModelFile normal = appleLeavesModel(name,modLoc("block/"+name),null);
        ModelFile flowers = appleLeavesModel(name+"_flowering",modLoc("block/"+name),modLoc("block/"+name+"_flowering_overlay"));
        ModelFile applesTwo = appleLeavesModel(name+"_mature",modLoc("block/"+name),modLoc("block/"+name+"_mature_overlay"));
        if (hasBudding) {
            ModelFile apples = appleLeavesModel(name+"_budding",modLoc("block/"+name),buddingLoc);
            return List.of(normal,flowers,apples,applesTwo);
        }
        return List.of(normal,flowers,applesTwo,applesTwo);
    }

    private BlockModelBuilder appleLeavesModel(String name, ResourceLocation leavesTexture, @Nullable ResourceLocation overlayTexture) {
        BlockModelBuilder model = models().withExistingParent(name,mcLoc("block/block")).texture("leaves", leavesTexture)
                .texture("particle", leavesTexture).renderType("minecraft:cutout_mipped");
        if (overlayTexture != null) {
            model.texture("overlay", overlayTexture);
        }

        model.element().from(0, 0, 0).to(16, 16, 16).allFaces((direction, face) -> face.texture("#leaves")
                .tintindex(0).cullface(direction)).end();
        if (overlayTexture != null) {
            model.element().from(-0.01F, -0.01F, -0.01F).to(16.01F, 16.01F, 16.01F).allFaces((direction, face) ->
                    face.texture("#overlay")).end();
        }
        return model;
    }
}
