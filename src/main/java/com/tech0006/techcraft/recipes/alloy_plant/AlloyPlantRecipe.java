package com.tech0006.techcraft.recipes.alloy_plant;

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

public class AlloyPlantRecipe implements IAlloyPlantRecipe {
    private final NonNullList<Ingredient> recipeItem;
    private final ItemStack recipeOutput;
    private final ResourceLocation id;
    private final String group;

    public AlloyPlantRecipe(NonNullList<Ingredient> recipeItem, ItemStack recipeOutput, ResourceLocation id, String group) {
        this.recipeItem = recipeItem;
        this.recipeOutput = recipeOutput;
        this.id = id;
        this.group = group;
    }

    @Override
    public boolean matches(RecipeWrapper inv, World worldIn) {
        boolean i1 = false, i2 = false, i3 = false;
        for (int i = 0; i < this.recipeItem.get(0).getMatchingStacks().length; i++) {
            if (inv.getStackInSlot(0).getItem() == this.recipeItem.get(0).getMatchingStacks()[i].getItem()) {
                i1 = true;
            }
        }
        for (int i = 0; i < this.recipeItem.get(1).getMatchingStacks().length; i++) {
            if (inv.getStackInSlot(1).getItem() == this.recipeItem.get(1).getMatchingStacks()[i].getItem()) {
                i2 = true;
            }
        }
        for (int i = 0; i < this.recipeItem.get(2).getMatchingStacks().length; i++) {
            if (inv.getStackInSlot(2).getItem() == this.recipeItem.get(2).getMatchingStacks()[i].getItem()) {
                i3 = true;
            }
        }

        if (recipeItem.get(0) == Ingredient.EMPTY && inv.getStackInSlot(0) == ItemStack.EMPTY) {
            i1 = true;
        }
        if (recipeItem.get(1) == Ingredient.EMPTY && inv.getStackInSlot(1) == ItemStack.EMPTY) {
            i2 = true;
        }
        if (recipeItem.get(2) == Ingredient.EMPTY && inv.getStackInSlot(2) == ItemStack.EMPTY) {
            i3 = true;
        }

        if (i1 && i2 && i3) {
            return true;
        } else {
            return false;
        }
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
        return RecipeSerializerInit.ALLOY_PLANT_RECIPE_SERIALIZER;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItem;
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
        return Ingredient.deserialize(p_199798_0_);
    }

    public static class AlloyPlantRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AlloyPlantRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("techcraft", "alloy_plant");

        public AlloyPlantRecipe read(ResourceLocation recipeId, JsonObject json) {
            String s = JSONUtils.getString(json, "group", "");
            Ingredient input1, input2, input3;
            if (json.has("input1"))
            {
                input1 = AlloyPlantRecipe.deserializeIn(JSONUtils.getJsonObject(json, "input1"));
            }
            else
            {
                input1 = Ingredient.EMPTY;
            }

            if (json.has("input2"))
            {
                input2 = AlloyPlantRecipe.deserializeIn(JSONUtils.getJsonObject(json, "input2"));
            }
            else
            {
                input2 = Ingredient.EMPTY;
            }

            if (json.has("input3"))
            {
                input3 = AlloyPlantRecipe.deserializeIn(JSONUtils.getJsonObject(json, "input3"));
            }
            else
            {
                input3 = Ingredient.EMPTY;
            }
            ItemStack itemstack = AlloyPlantRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));

            NonNullList<Ingredient> inputs = NonNullList.create();
            inputs.add(input1);
            inputs.add(input2);
            inputs.add(input3);

            return new AlloyPlantRecipe(inputs, itemstack, recipeId, s);
        }

        public AlloyPlantRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            String s = buffer.readString(32767);
            Ingredient input1 = Ingredient.EMPTY;
            Ingredient input2 = Ingredient.EMPTY;
            Ingredient input3 = Ingredient.EMPTY;

            input1 = Ingredient.read(buffer);
            input2 = Ingredient.read(buffer);
            input3 = Ingredient.read(buffer);

            ItemStack itemstack = buffer.readItemStack();

            NonNullList<Ingredient> inputs = NonNullList.create();
            inputs.add(input1);
            inputs.add(input2);
            inputs.add(input3);

            return new AlloyPlantRecipe(inputs, itemstack, recipeId, s);
        }

        public void write(PacketBuffer buffer, AlloyPlantRecipe recipe) {
            buffer.writeString(recipe.group);

            Ingredient ingredient1 = recipe.recipeItem.get(0);
            ingredient1.write(buffer);

            Ingredient ingredient2 = recipe.recipeItem.get(1);
            ingredient2.write(buffer);

            Ingredient ingredient3 = recipe.recipeItem.get(2);
            ingredient3.write(buffer);

            buffer.writeItemStack(recipe.recipeOutput);
        }
    }
}