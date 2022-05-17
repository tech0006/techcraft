package com.tech0006.techcraft.integration.modules.jei.recipeCategory;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tech0006.techcraft.common.registration.TCItems;
import com.tech0006.techcraft.recipes.electric_crusher.ElectricCrusherRecipe;
import com.tech0006.techcraft.techcraft;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ElectricCrusherRecipeCategory implements IRecipeCategory<ElectricCrusherRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(techcraft.MOD_ID, "electric_crusher");

    private final Component localizedName;

    private final IDrawable background;

    private final IDrawable icon;

    private final IDrawableAnimated arrow, energyBar;

    public ElectricCrusherRecipeCategory(IGuiHelper guiHelper) {
        this.localizedName = new TextComponent(I18n.get("block.techcraft.electric_crusher"));

        ResourceLocation location = new ResourceLocation(techcraft.MOD_ID, "textures/gui/electric_crusher.png");
        this.background = guiHelper.createDrawable(location, 8, 10, 116, 66);

        this.icon = guiHelper.createDrawableIngredient(new ItemStack(TCItems.ELECTRIC_CRUSHER.get(), 1));

        this.arrow = guiHelper.drawableBuilder(location, 176, 60, 24, 18).buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
        this.energyBar = guiHelper.drawableBuilder(location, 176, 0, 16, 60).buildAnimated(1500, IDrawableAnimated.StartDirection.TOP, true);
    }

    @Override
    public void draw(ElectricCrusherRecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, matrixStack, mouseX, mouseY);
        this.arrow.draw(matrixStack, 66, 19);
        this.energyBar.draw(matrixStack, 2, 2);
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends ElectricCrusherRecipe> getRecipeClass() {
        return ElectricCrusherRecipe.class;
    }

    @Override
    public Component getTitle() {
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
    public void setIngredients(ElectricCrusherRecipe recipe, IIngredients iIngredients) {
        iIngredients.setInputIngredients(recipe.getIngredients().subList(0, recipe.getIngredients().size()));
        List<ItemStack> outputs = new ArrayList<>(1);
        outputs.add(recipe.getResultItem());
        iIngredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ElectricCrusherRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 41, 19);
        itemStacks.init(1, false, 96, 19);
        itemStacks.set(ingredients);
    }
}