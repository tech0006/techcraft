package com.tech0006.techcraft.integration.modules.jei;


import com.tech0006.techcraft.blocks.TileEntity.AlloyPlantTileEntity;
import com.tech0006.techcraft.blocks.TileEntity.TCbenchTileEntity;
import com.tech0006.techcraft.blocks.TileEntity.TCforgeTileEntity;
import com.tech0006.techcraft.blocks.TileEntity.WireShaperTileEntity;
import com.tech0006.techcraft.blocks.WireShaper;
import com.tech0006.techcraft.init.Items;
import com.tech0006.techcraft.init.RecipeSerializerInit;
import com.tech0006.techcraft.integration.modules.jei.recipeCategory.AlloyPlantRecipeCategory;
import com.tech0006.techcraft.integration.modules.jei.recipeCategory.TCbenchRecipeCategory;
import com.tech0006.techcraft.integration.modules.jei.recipeCategory.TCforgeRecipeCategory;
import com.tech0006.techcraft.integration.modules.jei.recipeCategory.WireShaperRecipeCategory;
import com.tech0006.techcraft.techcraft;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class Plugin implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(techcraft.MOD_ID, "core");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IModPlugin.super.registerCategories(registration);
        registration.addRecipeCategories(new TCbenchRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new TCforgeRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new WireShaperRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new AlloyPlantRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        IModPlugin.super.registerRecipes(registration);
        registration.addRecipes(TCbenchTileEntity.findRecipesByType(RecipeSerializerInit.TC_BENCH_TYPE), TCbenchRecipeCategory.UID);
        registration.addRecipes(TCforgeTileEntity.findRecipesByType(RecipeSerializerInit.TC_FORGE_TYPE), TCforgeRecipeCategory.UID);
        registration.addRecipes(WireShaperTileEntity.findRecipesByType(RecipeSerializerInit.WIRE_SHAPER_TYPE), WireShaperRecipeCategory.UID);
        registration.addRecipes(AlloyPlantTileEntity.findRecipesByType(RecipeSerializerInit.ALLOY_PLANT_TYPE), AlloyPlantRecipeCategory.UID);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        IModPlugin.super.registerRecipeCatalysts(registration);

        ItemStack grindstone = new ItemStack(Items.TC_BENCH.get(), 1);
        registration.addRecipeCatalyst(grindstone, TCbenchRecipeCategory.UID);

        grindstone = new ItemStack(Items.TC_FORGE.get(), 1);
        registration.addRecipeCatalyst(grindstone, TCforgeRecipeCategory.UID);

        grindstone = new ItemStack(Items.WIRE_SHAPER.get(), 1);
        registration.addRecipeCatalyst(grindstone, WireShaperRecipeCategory.UID);

        grindstone = new ItemStack(Items.ALLOY_PLANT.get(), 1);
        registration.addRecipeCatalyst(grindstone, AlloyPlantRecipeCategory.UID);
    }

}
