package com.tech0006.techcraft.init;

import com.tech0006.techcraft.blocks.WireShaper;
import com.tech0006.techcraft.recipes.tc_bench.ITCbenchRecipe;
import com.tech0006.techcraft.recipes.tc_bench.TCbenchRecipe;
import com.tech0006.techcraft.recipes.tc_forge.ITCforgeRecipe;
import com.tech0006.techcraft.recipes.tc_forge.TCforgeRecipe;
import com.tech0006.techcraft.recipes.wire_shaper.IWireShaperRecipe;
import com.tech0006.techcraft.recipes.wire_shaper.WireShaperRecipe;
import com.tech0006.techcraft.techcraft;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeSerializerInit {

    public static final IRecipeSerializer<TCbenchRecipe> TC_BENCH_RECIPE_SERIALIZER = new TCbenchRecipe.TCbenchRecipeSerializer();
    public static final IRecipeType<ITCbenchRecipe> TC_BENCH_TYPE = registerType(ITCbenchRecipe.RECIPE_TYPE_ID);


    public static final IRecipeSerializer<TCforgeRecipe> TC_FORGE_RECIPE_SERIALIZER = new TCforgeRecipe.TCforgeRecipeSerializer();
    public static final IRecipeType<ITCforgeRecipe> TC_FORGE_TYPE = registerType(ITCforgeRecipe.RECIPE_TYPE_ID);

    public static final IRecipeSerializer<WireShaperRecipe> WIRE_SHAPER_RECIPE_SERIALIZER = new WireShaperRecipe.WireShaperRecipeSerializer();
    public static final IRecipeType<IWireShaperRecipe> WIRE_SHAPER_TYPE = registerType(IWireShaperRecipe.RECIPE_TYPE_ID);


    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = new DeferredRegister<>(
            ForgeRegistries.RECIPE_SERIALIZERS, techcraft.MOD_ID);


    public static final RegistryObject<IRecipeSerializer<?>> TC_BENCH_SERIALIZER = RECIPE_SERIALIZERS.register("tc_bench",
            () -> TC_BENCH_RECIPE_SERIALIZER);
    public static final RegistryObject<IRecipeSerializer<?>> TC_FORGE_SERIALIZER = RECIPE_SERIALIZERS.register("tc_forge",
            () -> TC_FORGE_RECIPE_SERIALIZER);
    public static final RegistryObject<IRecipeSerializer<?>> WIRE_SHAPER_SERIALIZER = RECIPE_SERIALIZERS.register("wire_shaper",
            () -> WIRE_SHAPER_RECIPE_SERIALIZER);


    private static class RecipeType<T extends IRecipe<?>> implements IRecipeType<T> {
        @Override
        public String toString() {
            return Registry.RECIPE_TYPE.getKey(this).toString();
        }
    }

    private static <T extends IRecipeType> T registerType(ResourceLocation recipeTypeId) {
        return (T) Registry.register(Registry.RECIPE_TYPE, recipeTypeId, new RecipeType<>());
    }
}