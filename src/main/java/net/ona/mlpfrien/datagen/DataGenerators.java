package net.ona.mlpfrien.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = MyLittlePonyCraftingFriends.MODID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherDat(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new LootTableProvider(output, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(LBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookup));
        generator.addProvider(event.includeServer(), new LRecipeProvider(output, lookup));

        generator.addProvider(event.includeServer(), new LDataPackProvider(output, lookup));

        BlockTagsProvider blockTagsProvider = new LBlockTagProvider(output, lookup, fileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new LItemTagProvider(output, lookup, blockTagsProvider.contentsGetter(), fileHelper));

        generator.addProvider(event.includeClient(), new LBlockstateProvider(output, fileHelper));
        generator.addProvider(event.includeClient(), new LItemModelProvider(output, fileHelper));
    }
}
