package com.tech0006.techcraft.integration.modules.jei.recipeCategory;

import com.tech0006.techcraft.common.registration.TCItems;
import com.tech0006.techcraft.recipes.wire_shaper.WireShaperRecipe;
import com.tech0006.techcraft.techcraft;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
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

public class WireShaperRecipeCategory implements IRecipeCategory<WireShaperRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(techcraft.MOD_ID, "wire_shaper");

    private final Component localizedName;

    private final IDrawable background;

    private final IDrawable icon;

    public WireShaperRecipeCategory(IGuiHelper guiHelper) {
        this.localizedName = new TextComponent(I18n.get("block.techcraft.wire_shaper"));

        ResourceLocation location = new ResourceLocation(techcraft.MOD_ID, "textures/gui/wire_shaper.png");
        this.background = guiHelper.createDrawable(location, 55, 33, 73, 19);

        this.icon = guiHelper.createDrawableIngredient(new ItemStack(TCItems.WIRE_SHAPER.get(), 1));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends WireShaperRecipe> getRecipeClass() {
        return WireShaperRecipe.class;
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
    public void setIngredients(WireShaperRecipe recipe, IIngredients iIngredients) {
        iIngredients.setInputIngredients(recipe.getIngredients().subList(0, recipe.getIngredients().size()));
        List<ItemStack> outputs = new ArrayList<>(1);
        outputs.add(recipe.getResultItem());
        iIngredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, WireShaperRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(0, true, 0, 0);
        itemStacks.init(1, false, 54, 0);
        itemStacks.set(ingredients);
    }
}
