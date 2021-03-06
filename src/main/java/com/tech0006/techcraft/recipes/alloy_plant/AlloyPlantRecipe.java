package com.tech0006.techcraft.recipes.alloy_plant;

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
import net.minecraftforge.common.crafting.CraftingHelper;
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
    public boolean matches(RecipeWrapper inv, Level levelIn) {
        boolean i1 = false, i2 = false, i3 = false;
        for (int i = 0; i < this.recipeItem.get(0).getItems().length; i++) {
            if (inv.getItem(0).getItem() == this.recipeItem.get(0).getItems()[i].getItem()) {
                i1 = true;
            }
        }
        for (int i = 0; i < this.recipeItem.get(1).getItems().length; i++) {
            if (inv.getItem(1).getItem() == this.recipeItem.get(1).getItems()[i].getItem()) {
                i2 = true;
            }
        }
        for (int i = 0; i < this.recipeItem.get(2).getItems().length; i++) {
            if (inv.getItem(2).getItem() == this.recipeItem.get(2).getItems()[i].getItem()) {
                i3 = true;
            }
        }

        if (recipeItem.get(0) == Ingredient.EMPTY && inv.getItem(0) == ItemStack.EMPTY) {
            i1 = true;
        }
        if (recipeItem.get(1) == Ingredient.EMPTY && inv.getItem(1) == ItemStack.EMPTY) {
            i2 = true;
        }
        if (recipeItem.get(2) == Ingredient.EMPTY && inv.getItem(2) == ItemStack.EMPTY) {
            i3 = true;
        }

        return i1 && i2 && i3;
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
        return TCRecipeSerializerInit.ALLOY_PLANT_RECIPE_SERIALIZER;
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
            return CraftingHelper.getItemStack(p_199798_0_, true);
        }
    }

    private static Ingredient deserializeIn(JsonObject p_199798_0_) {
        return Ingredient.fromJson(p_199798_0_);
    }

    public static class AlloyPlantRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<AlloyPlantRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("techcraft", "alloy_plant");

        public AlloyPlantRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            String s = GsonHelper.getAsString(json, "group", "");
            Ingredient input1, input2, input3;
            if (json.has("input1"))
            {
                input1 = AlloyPlantRecipe.deserializeIn(GsonHelper.getAsJsonObject(json, "input1"));
            }
            else
            {
                input1 = Ingredient.EMPTY;
            }

            if (json.has("input2"))
            {
                input2 = AlloyPlantRecipe.deserializeIn(GsonHelper.getAsJsonObject(json, "input2"));
            }
            else
            {
                input2 = Ingredient.EMPTY;
            }

            if (json.has("input3"))
            {
                input3 = AlloyPlantRecipe.deserializeIn(GsonHelper.getAsJsonObject(json, "input3"));
            }
            else
            {
                input3 = Ingredient.EMPTY;
            }
            ItemStack itemstack = AlloyPlantRecipe.deserializeItem(GsonHelper.getAsJsonObject(json, "result"));

            NonNullList<Ingredient> inputs = NonNullList.create();
            inputs.add(input1);
            inputs.add(input2);
            inputs.add(input3);

            return new AlloyPlantRecipe(inputs, itemstack, recipeId, s);
        }

        public AlloyPlantRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            String s = buffer.readUtf(32767);
            Ingredient input1;
            Ingredient input2;
            Ingredient input3;

            input1 = Ingredient.fromNetwork(buffer);
            input2 = Ingredient.fromNetwork(buffer);
            input3 = Ingredient.fromNetwork(buffer);

            ItemStack itemstack = buffer.readItem();

            NonNullList<Ingredient> inputs = NonNullList.create();
            inputs.add(input1);
            inputs.add(input2);
            inputs.add(input3);

            return new AlloyPlantRecipe(inputs, itemstack, recipeId, s);
        }

        public void toNetwork(FriendlyByteBuf buffer, AlloyPlantRecipe recipe) {
            buffer.writeUtf(recipe.group);

            Ingredient ingredient1 = recipe.recipeItem.get(0);
            ingredient1.toNetwork(buffer);

            Ingredient ingredient2 = recipe.recipeItem.get(1);
            ingredient2.toNetwork(buffer);

            Ingredient ingredient3 = recipe.recipeItem.get(2);
            ingredient3.toNetwork(buffer);

            buffer.writeItem(recipe.recipeOutput);
        }
    }
}