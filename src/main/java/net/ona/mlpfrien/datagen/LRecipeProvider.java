package net.ona.mlpfrien.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.ona.mlpfrien.init.PonyItems;
import net.ona.mlpfrien.init.PonyTags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class LRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public LRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PonyItems.HAY_BURGUER)
                .pattern(" A ")
                .pattern("LDL")
                .pattern(" A ")
                .define('D', Items.WHEAT)
                .define('L', ItemTags.LEAVES)
                .define('A',Items.BREAD)
                .unlockedBy("hay_burg",has(Items.WHEAT)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, PonyItems.CARAMEL_APPLE)
                .requires(PonyTags.APPLES)
                .requires(Items.SUGAR)
                .unlockedBy("hay_burg",has(Items.APPLE)).save(recipeOutput);
    }
}
