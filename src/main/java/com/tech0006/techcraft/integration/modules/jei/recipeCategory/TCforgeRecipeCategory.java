package com.tech0006.techcraft.integration.modules.jei.recipeCategory;

import com.tech0006.techcraft.common.registration.TCItems;
import com.tech0006.techcraft.recipes.tc_forge.TCforgeRecipe;
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

public class TCforgeRecipeCategory implements IRecipeCategory<TCforgeRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(techcraft.MOD_ID, "tc_forge");

    private final String localizedName;

    private final IDrawable background;

    private final IDrawable icon;

    public TCforgeRecipeCategory(IGuiHelper guiHelper) {
        this.localizedName = I18n.get("block.techcraft.tc_forge");

        ResourceLocation location = new ResourceLocation(techcraft.MOD_ID, "textures/gui/tc_forge.png");
        this.background = guiHelper.createDrawable(location, 55, 33, 73, 19);

        this.icon = guiHelper.createDrawableIngredient(new ItemStack(TCItems.TC_FORGE.get(), 1));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends TCforgeRecipe> getRecipeClass() {
        return TCforgeRecipe.class;
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
    public void setIngredients(TCforgeRecipe recipe, IIngredients iIngredients) {
        iIngredients.setInputIngredients(recipe.getIngredients().subList(0, recipe.getIngredients().size()));
        List<ItemStack> outputs = new ArrayList<>(1);
        outputs.add(recipe.getResultItem());
        iIngredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, TCforgeRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 0, 0);
        itemStacks.init(1, false, 54, 0);
        itemStacks.set(ingredients);
    }
}
