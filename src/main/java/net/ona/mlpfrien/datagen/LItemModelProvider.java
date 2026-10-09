package net.ona.mlpfrien.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.init.PonyItems;

public class LItemModelProvider extends ItemModelProvider {
    public LItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MyLittlePonyCraftingFriends.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(PonyItems.HAY_BURGUER.get());
        basicItem(PonyItems.CARAMEL_APPLE.get());
        basicItem(PonyItems.GREEN_APPLE.get());
        basicItem(PonyItems.SOUR_APPLE.get());

    }

    private void heldItem(DeferredItem<?> item) {
        this.withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath("mlpfrien","item/"+item.getId().getPath()));
    }
}
