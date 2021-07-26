package com.tech0006.techcraft.integration.modules.jei.recipeCategory;

import com.tech0006.techcraft.init.Items;
import com.tech0006.techcraft.recipes.tc_bench.TCbenchRecipe;
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

public class TCbenchRecipeCategory implements IRecipeCategory<TCbenchRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(techcraft.MOD_ID, "tc_bench");

    private final String localizedName;

    private final IDrawable background;

    private final IDrawable icon;

    public TCbenchRecipeCategory(IGuiHelper guiHelper) {
        this.localizedName = I18n.get("block.techcraft.tc_bench");

        ResourceLocation location = new ResourceLocation(techcraft.MOD_ID, "textures/gui/tc_bench.png");
        this.background = guiHelper.createDrawable(location, 16, 16, 136, 109);

        this.icon = guiHelper.createDrawableIngredient(new ItemStack(Items.TC_BENCH.get(), 1));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends TCbenchRecipe> getRecipeClass() {
        return TCbenchRecipe.class;
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
    public void setIngredients(TCbenchRecipe recipe, IIngredients iIngredients) {
        iIngredients.setInputIngredients(recipe.getIngredients().subList(0, recipe.getIngredients().size()));
        List<ItemStack> outputs = new ArrayList<>(1);
        outputs.add(recipe.getResultItem());
        iIngredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, TCbenchRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                itemStacks.init(i * 6 + j, true, j * 18, i * 18);
            }
        }
        itemStacks.init(36, false, 117, 45);
        itemStacks.set(ingredients);
    }
}
