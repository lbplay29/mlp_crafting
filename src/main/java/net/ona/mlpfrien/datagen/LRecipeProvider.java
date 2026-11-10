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
import net.ona.mlpfrien.init.PonyBlocks;
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
                .unlockedBy("cara_apel",has(Items.APPLE)).save(recipeOutput);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, PonyBlocks.GOLDEN_PLANKS.get(),4)
                .requires(PonyTags.GOLDEN_LOGS)
                .unlockedBy("goldn_plks",has(PonyBlocks.GOLDEN_LOG)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PonyBlocks.GOLDEN_WOOD,3)
                .pattern("AA")
                .pattern("AA")
                .define('A',PonyBlocks.GOLDEN_LOG).unlockedBy("goldn_wd",has(PonyBlocks.GOLDEN_LOG)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PonyBlocks.STRIPPED_GOLDEN_WOOD,3)
                .pattern("AA")
                .pattern("AA")
                .define('A',PonyBlocks.STRIPPED_GOLDEN_LOG).unlockedBy("goldn_wd_strp",has(PonyBlocks.GOLDEN_LOG)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PonyBlocks.GOLDEN_STAIRS,4)
                .pattern("  A")
                .pattern(" AA")
                .pattern("AAA")
                .define('A',PonyBlocks.GOLDEN_PLANKS).unlockedBy("goldn_strs",has(PonyBlocks.GOLDEN_PLANKS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PonyBlocks.GOLDEN_SLAB,6)
                .pattern("AAA")
                .define('A',PonyBlocks.GOLDEN_PLANKS).unlockedBy("goldn_slb",has(PonyBlocks.GOLDEN_PLANKS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PonyBlocks.GOLDEN_TRAPDOOR,2)
                .pattern("AAA")
                .pattern("AAA")
                .define('A',PonyBlocks.GOLDEN_PLANKS).unlockedBy("goldn_trpdr",has(PonyBlocks.GOLDEN_PLANKS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PonyBlocks.GOLDEN_DOOR,3)
                .pattern("AA")
                .pattern("AA")
                .pattern("AA")
                .define('A',PonyBlocks.GOLDEN_PLANKS).unlockedBy("goldn_dr",has(PonyBlocks.GOLDEN_PLANKS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PonyBlocks.GOLDEN_BUTTON,1)
                .pattern("A")
                .define('A',PonyBlocks.GOLDEN_PLANKS).unlockedBy("goldn_btn",has(PonyBlocks.GOLDEN_PLANKS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PonyBlocks.GOLDEN_PRESSURE_PlATE,1)
                .pattern("AA")
                .define('A',PonyBlocks.GOLDEN_PLANKS).unlockedBy("goldn_prpl",has(PonyBlocks.GOLDEN_PLANKS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PonyBlocks.GOLDEN_FENCE,3)
                .pattern("ASA")
                .pattern("ASA")
                .define('A',PonyBlocks.GOLDEN_PLANKS)
                .define('S',Items.STICK).unlockedBy("goldn_fnc",has(PonyBlocks.GOLDEN_PLANKS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PonyBlocks.GOLDEN_FENCE_GATE,1)
                .pattern("SAS")
                .pattern("SAS")
                .define('A',PonyBlocks.GOLDEN_PLANKS)
                .define('S',Items.STICK).unlockedBy("goldn_fnc_gt",has(PonyBlocks.GOLDEN_PLANKS)).save(recipeOutput);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, PonyBlocks.ZAP_PLANKS.get(),4)
                .requires(PonyTags.ZAP_LOGS)
                .unlockedBy("zap_plks",has(PonyBlocks.ZAP_LOG)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PonyBlocks.ZAP_WOOD,3)
                .pattern("AA")
                .pattern("AA")
                .define('A',PonyBlocks.ZAP_LOG).unlockedBy("zap_wd",has(PonyBlocks.ZAP_LOG)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PonyBlocks.STRIPPED_ZAP_WOOD,3)
                .pattern("AA")
                .pattern("AA")
                .define('A',PonyBlocks.STRIPPED_ZAP_LOG).unlockedBy("zap_wd",has(PonyBlocks.ZAP_LOG)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PonyBlocks.ZAP_STAIRS,4)
                .pattern("  A")
                .pattern(" AA")
                .pattern("AAA")
                .define('A',PonyBlocks.ZAP_PLANKS).unlockedBy("zap_strs",has(PonyBlocks.ZAP_PLANKS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PonyBlocks.ZAP_SLAB,6)
                .pattern("AAA")
                .define('A',PonyBlocks.ZAP_PLANKS).unlockedBy("zap_slb",has(PonyBlocks.ZAP_PLANKS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PonyBlocks.ZAP_TRAPDOOR,2)
                .pattern("AAA")
                .pattern("AAA")
                .define('A',PonyBlocks.ZAP_PLANKS).unlockedBy("zap_trpdr",has(PonyBlocks.ZAP_PLANKS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PonyBlocks.ZAP_DOOR,3)
                .pattern("AA")
                .pattern("AA")
                .pattern("AA")
                .define('A',PonyBlocks.ZAP_PLANKS).unlockedBy("zap_dr",has(PonyBlocks.ZAP_PLANKS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PonyBlocks.ZAP_BUTTON,1)
                .pattern("A")
                .define('A',PonyBlocks.ZAP_PLANKS).unlockedBy("zap_btn",has(PonyBlocks.ZAP_PLANKS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PonyBlocks.ZAP_PRESSURE_PlATE,1)
                .pattern("AA")
                .define('A',PonyBlocks.ZAP_PLANKS).unlockedBy("zap_prpl",has(PonyBlocks.ZAP_PLANKS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PonyBlocks.ZAP_FENCE,3)
                .pattern("ASA")
                .pattern("ASA")
                .define('A',PonyBlocks.ZAP_PLANKS)
                .define('S',Items.STICK).unlockedBy("zap_fnc",has(PonyBlocks.ZAP_PLANKS)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, PonyBlocks.ZAP_FENCE_GATE,1)
                .pattern("SAS")
                .pattern("SAS")
                .define('A',PonyBlocks.ZAP_PLANKS)
                .define('S',Items.STICK).unlockedBy("zap_fnc_gt",has(PonyBlocks.ZAP_PLANKS)).save(recipeOutput);
    }
}
