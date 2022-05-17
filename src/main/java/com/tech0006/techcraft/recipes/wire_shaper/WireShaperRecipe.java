package com.tech0006.techcraft.recipes.wire_shaper;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.tech0006.techcraft.common.registration.TCRecipeSerializerInit;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistryEntry;


public class WireShaperRecipe implements IWireShaperRecipe {
    private final Ingredient recipeItem;
    private final ItemStack recipeOutput;
    private final ResourceLocation id;
    private final String group;

    public WireShaperRecipe(Ingredient recipeItem, ItemStack recipeOutput, ResourceLocation id, String group) {
        this.recipeItem = recipeItem;
        this.recipeOutput = recipeOutput;
        this.id = id;
        this.group = group;
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level worldIn) {
        for (int i = 0; i < this.recipeItem.getItems().length; i++) {
            if (inv.getItem(0).getItem() == this.recipeItem.getItems()[i].getItem()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv) {
        return this.getResultItem().copy();
    }

    @Override
    public ItemStack getResultItem() {
        return this.recipeOutput;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return TCRecipeSerializerInit.WIRE_SHAPER_RECIPE_SERIALIZER;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ing = NonNullList.create();
        ing.add(this.recipeItem);
        return ing;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    public static ItemStack deserializeItem(JsonObject p_199798_0_) {
        String s = GsonHelper.getAsString(p_199798_0_, "item");
        Item item = Registry.ITEM.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + s + "'");
        });
        if (p_199798_0_.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int i = GsonHelper.getAsInt(p_199798_0_, "count", 1);
            return net.minecraftforge.common.crafting.CraftingHelper.getItemStack(p_199798_0_, true);
        }
    }

    private static Ingredient deserializeIn(JsonObject p_199798_0_) {
        return Ingredient.fromJson(p_199798_0_);
    }

    public static class WireShaperRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<WireShaperRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("techcraft", "wire_shaper");

        public WireShaperRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            String s = GsonHelper.getAsString(json, "group", "");
            Ingredient input = WireShaperRecipe.deserializeIn(GsonHelper.getAsJsonObject(json, "input"));
            ItemStack itemstack = WireShaperRecipe.deserializeItem(GsonHelper.getAsJsonObject(json, "result"));
            return new WireShaperRecipe(input, itemstack, recipeId, s);
        }

        public WireShaperRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            String s = buffer.readUtf(32767);
            Ingredient ing;

            ing = Ingredient.fromNetwork(buffer);


            ItemStack itemstack = buffer.readItem();
            return new WireShaperRecipe(ing, itemstack, recipeId, s);
        }

        public void toNetwork(FriendlyByteBuf buffer, WireShaperRecipe recipe) {
            buffer.writeUtf(recipe.group);
            recipe.recipeItem.toNetwork(buffer);
            buffer.writeItem(recipe.recipeOutput);
        }
    }
}