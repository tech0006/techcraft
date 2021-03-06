package com.tech0006.techcraft.integration.modules.jei;


import com.tech0006.techcraft.blocks.blockentity.functional.*;
import com.tech0006.techcraft.common.registration.TCItems;
import com.tech0006.techcraft.common.registration.TCRecipeSerializerInit;
import com.tech0006.techcraft.integration.modules.jei.recipeCategory.*;
import com.tech0006.techcraft.techcraft;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;


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
        registration.addRecipeCategories(new ElectricCrusherRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ElectricFurnaceRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        IModPlugin.super.registerRecipes(registration);
        registration.addRecipes(TCbenchBlockEntity.findRecipesByType(TCRecipeSerializerInit.TC_BENCH_TYPE), TCbenchRecipeCategory.UID);
        registration.addRecipes(TCforgeBlockEntity.findRecipesByType(TCRecipeSerializerInit.TC_FORGE_TYPE), TCforgeRecipeCategory.UID);
        registration.addRecipes(WireShaperBlockEntity.findRecipesByType(TCRecipeSerializerInit.WIRE_SHAPER_TYPE), WireShaperRecipeCategory.UID);
        registration.addRecipes(AlloyPlantBlockEntity.findRecipesByType(TCRecipeSerializerInit.ALLOY_PLANT_TYPE), AlloyPlantRecipeCategory.UID);
        registration.addRecipes(ElectricCrusherBlockEntity.findRecipesByType(TCRecipeSerializerInit.ELECTRIC_CRUSHER_TYPE), ElectricCrusherRecipeCategory.UID);
        registration.addRecipes(ElectricFurnaceBlockEntity.findRecipesByType(RecipeType.BLASTING), ElectricFurnaceRecipeCategory.UID);
        registration.addRecipes(ElectricFurnaceBlockEntity.findRecipesByType(RecipeType.SMELTING), ElectricFurnaceRecipeCategory.UID);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        IModPlugin.super.registerRecipeCatalysts(registration);

        ItemStack stack = new ItemStack(TCItems.TC_BENCH.get(), 1);
        registration.addRecipeCatalyst(stack, TCbenchRecipeCategory.UID);

        stack = new ItemStack(TCItems.TC_FORGE.get(), 1);
        registration.addRecipeCatalyst(stack, TCforgeRecipeCategory.UID);

        stack = new ItemStack(TCItems.WIRE_SHAPER.get(), 1);
        registration.addRecipeCatalyst(stack, WireShaperRecipeCategory.UID);

        stack = new ItemStack(TCItems.ALLOY_PLANT.get(), 1);
        registration.addRecipeCatalyst(stack, AlloyPlantRecipeCategory.UID);

        stack = new ItemStack(TCItems.ELECTRIC_CRUSHER.get(), 1);
        registration.addRecipeCatalyst(stack, ElectricCrusherRecipeCategory.UID);

        stack = new ItemStack(TCItems.ELECTRIC_FURNACE.get(), 1);
        registration.addRecipeCatalyst(stack, ElectricFurnaceRecipeCategory.UID);
    }

}
