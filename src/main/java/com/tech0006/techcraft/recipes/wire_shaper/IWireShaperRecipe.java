package com.tech0006.techcraft.recipes.wire_shaper;

import com.tech0006.techcraft.techcraft;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

public interface IWireShaperRecipe extends IRecipe<RecipeWrapper> {

    ResourceLocation RECIPE_TYPE_ID = new ResourceLocation(techcraft.MOD_ID, "wire_shaper");

    @Nonnull
    @Override
    default IRecipeType<?> getType() {
        return Registry.RECIPE_TYPE.getValue(RECIPE_TYPE_ID).get();
    }

    @Override
    default boolean canFit(int width, int height) {
        return false;
    }
}