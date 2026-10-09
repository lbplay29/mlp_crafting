package net.ona.mlpfrien.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;

public class LBlockstateProvider extends BlockStateProvider {
    public LBlockstateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MyLittlePonyCraftingFriends.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        //basicBlock(BedrockedBlocks.ENCRUSTED_DEEPSLATE);
    }

    private void basicBlock(DeferredBlock<?> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }
}
