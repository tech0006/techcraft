package com.tech0006.techcraft.integration.modules.jei.recipeCategory;

import com.tech0006.techcraft.blocks.AlloyPlant;
import com.tech0006.techcraft.init.Items;
import com.tech0006.techcraft.recipes.alloy_plant.AlloyPlantRecipe;
import com.tech0006.techcraft.techcraft;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class AlloyPlantRecipeCategory implements IRecipeCategory<AlloyPlantRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(techcraft.MOD_ID, "alloy_plant");

    private final String localizedName;

    private final IDrawable background;

    private final IDrawable icon;

    public AlloyPlantRecipeCategory(IGuiHelper guiHelper) {
        this.localizedName = I18n.format("block.techcraft.alloy_plant");

        ResourceLocation location = new ResourceLocation(techcraft.MOD_ID, "textures/gui/alloy_plant.png");
        this.background = guiHelper.createDrawable(location, 49, 9, 73, 66);

        this.icon = guiHelper.createDrawableIngredient(new ItemStack(Items.ALLOY_PLANT.get(), 1));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends AlloyPlantRecipe> getRecipeClass() {
        return AlloyPlantRecipe.class;
    }

    @Override
    public String getTitle() {
        return this.localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(AlloyPlantRecipe recipe, IIngredients iIngredients) {
        iIngredients.setInputIngredients(recipe.getIngredients().subList(0, recipe.getIngredients().size()));
        List<ItemStack> outputs = new ArrayList<>(1);
        outputs.add(recipe.getRecipeOutput());
        iIngredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, AlloyPlantRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 0, 0);
        itemStacks.init(1, true,0, 24);
        itemStacks.init(2, true,0, 48);
        itemStacks.init(3, false,55, 24);
        itemStacks.set(ingredients);
    }
}

