package com.tech0006.techcraft.recipes.electric_crusher;

import com.tech0006.techcraft.techcraft;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

public interface IElectricCrusherRecipe extends IRecipe<RecipeWrapper> {

    ResourceLocation RECIPE_TYPE_ID = new ResourceLocation(techcraft.MOD_ID, "electric_crusher");

    @Nonnull
    @Override
    default IRecipeType<?> getType() {
        return Registry.RECIPE_TYPE.getOptional(RECIPE_TYPE_ID).get();
    }

    @Override
    default boolean canCraftInDimensions(int width, int height) {
        return false;
    }
}