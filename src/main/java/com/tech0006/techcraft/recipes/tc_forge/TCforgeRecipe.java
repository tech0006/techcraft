package com.tech0006.techcraft.recipes.tc_forge;


import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.tech0006.techcraft.init.RecipeSerializerInit;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistryEntry;


public class TCforgeRecipe implements ITCforgeRecipe {
    private final Ingredient recipeItem;
    private final ItemStack recipeOutput;
    private final ResourceLocation id;
    private final String group;

    public TCforgeRecipe(Ingredient recipeItem, ItemStack recipeOutput, ResourceLocation id, String group) {
        this.recipeItem = recipeItem;
        this.recipeOutput = recipeOutput;
        this.id = id;
        this.group = group;
    }

    @Override
    public boolean matches(RecipeWrapper inv, World worldIn) {
        for (int i = 0; i < this.recipeItem.getMatchingStacks().length; i++) {
            if (inv.getStackInSlot(0).getItem() == this.recipeItem.getMatchingStacks()[i].getItem()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ItemStack getCraftingResult(RecipeWrapper inv) {
        return this.getRecipeOutput().copy();
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.TC_FORGE_RECIPE_SERIALIZER;
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
    public boolean canFit(int width, int height) {
        return false;
    }

    public static ItemStack deserializeItem(JsonObject p_199798_0_) {
        String s = JSONUtils.getString(p_199798_0_, "item");
        Item item = Registry.ITEM.getValue(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + s + "'");
        });
        if (p_199798_0_.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int i = JSONUtils.getInt(p_199798_0_, "count", 1);
            return net.minecraftforge.common.crafting.CraftingHelper.getItemStack(p_199798_0_, true);
        }
    }

    private static Ingredient deserializeIn(JsonObject p_199798_0_) {

        String s = JSONUtils.getString(p_199798_0_, "item");
        Item item = Registry.ITEM.getValue(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + s + "'");
        });
        if (p_199798_0_.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int i = JSONUtils.getInt(p_199798_0_, "count", 1);
            return Ingredient.fromStacks(net.minecraftforge.common.crafting.CraftingHelper.getItemStack(p_199798_0_, true));
        }
    }

    public static class TCforgeRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<TCforgeRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("techcraft", "tc_forge");

        public TCforgeRecipe read(ResourceLocation recipeId, JsonObject json) {
            String s = JSONUtils.getString(json, "group", "");
            Ingredient input = TCforgeRecipe.deserializeIn(JSONUtils.getJsonObject(json, "input"));
            ItemStack itemstack = TCforgeRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            return new TCforgeRecipe(input, itemstack, recipeId, s);
        }

        public TCforgeRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            String s = buffer.readString(32767);
            Ingredient ing = Ingredient.EMPTY;

            ing = Ingredient.read(buffer);


            ItemStack itemstack = buffer.readItemStack();
            return new TCforgeRecipe(ing, itemstack, recipeId, s);
        }

        public void write(PacketBuffer buffer, TCforgeRecipe recipe) {
            buffer.writeString(recipe.group);
            Ingredient ingredient = recipe.recipeItem;
            ingredient.write(buffer);
            buffer.writeItemStack(recipe.recipeOutput);
        }
    }
}