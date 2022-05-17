package com.tech0006.techcraft.common.registration;

import com.tech0006.techcraft.recipes.alloy_plant.AlloyPlantRecipe;
import com.tech0006.techcraft.recipes.alloy_plant.IAlloyPlantRecipe;
import com.tech0006.techcraft.recipes.electric_crusher.ElectricCrusherRecipe;
import com.tech0006.techcraft.recipes.electric_crusher.IElectricCrusherRecipe;
import com.tech0006.techcraft.recipes.tc_bench.ITCbenchRecipe;
import com.tech0006.techcraft.recipes.tc_bench.TCbenchRecipe;
import com.tech0006.techcraft.recipes.tc_forge.ITCforgeRecipe;
import com.tech0006.techcraft.recipes.tc_forge.TCforgeRecipe;
import com.tech0006.techcraft.recipes.wire_shaper.IWireShaperRecipe;
import com.tech0006.techcraft.recipes.wire_shaper.WireShaperRecipe;
import com.tech0006.techcraft.techcraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class TCRecipeSerializerInit {

    public static final RecipeSerializer<TCbenchRecipe> TC_BENCH_RECIPE_SERIALIZER = new TCbenchRecipe.TCbenchRecipeSerializer();
    public static final RecipeType<ITCbenchRecipe> TC_BENCH_TYPE = registerType(ITCbenchRecipe.RECIPE_TYPE_ID);


    public static final RecipeSerializer<TCforgeRecipe> TC_FORGE_RECIPE_SERIALIZER = new TCforgeRecipe.TCforgeRecipeSerializer();
    public static final RecipeType<ITCforgeRecipe> TC_FORGE_TYPE = registerType(ITCforgeRecipe.RECIPE_TYPE_ID);

    public static final RecipeSerializer<WireShaperRecipe> WIRE_SHAPER_RECIPE_SERIALIZER = new WireShaperRecipe.WireShaperRecipeSerializer();
    public static final RecipeType<IWireShaperRecipe> WIRE_SHAPER_TYPE = registerType(IWireShaperRecipe.RECIPE_TYPE_ID);

    public static final RecipeSerializer<AlloyPlantRecipe> ALLOY_PLANT_RECIPE_SERIALIZER = new AlloyPlantRecipe.AlloyPlantRecipeSerializer();
    public static final RecipeType<IAlloyPlantRecipe> ALLOY_PLANT_TYPE = registerType(IAlloyPlantRecipe.RECIPE_TYPE_ID);

    public static final RecipeSerializer<ElectricCrusherRecipe> ELECTRIC_CRUSHER_RECIPE_SERIALIZER = new ElectricCrusherRecipe.ElectricCrusherRecipeSerializer();
    public static final RecipeType<IElectricCrusherRecipe> ELECTRIC_CRUSHER_TYPE = registerType(IElectricCrusherRecipe.RECIPE_TYPE_ID);

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(
            ForgeRegistries.RECIPE_SERIALIZERS, techcraft.MOD_ID);


    public static final RegistryObject<RecipeSerializer<?>> TC_BENCH_SERIALIZER = RECIPE_SERIALIZERS.register("tc_bench",
            () -> TC_BENCH_RECIPE_SERIALIZER);
    public static final RegistryObject<RecipeSerializer<?>> TC_FORGE_SERIALIZER = RECIPE_SERIALIZERS.register("tc_forge",
            () -> TC_FORGE_RECIPE_SERIALIZER);
    public static final RegistryObject<RecipeSerializer<?>> WIRE_SHAPER_SERIALIZER = RECIPE_SERIALIZERS.register("wire_shaper",
            () -> WIRE_SHAPER_RECIPE_SERIALIZER);
    public static final RegistryObject<RecipeSerializer<?>> ALLOY_PLANT_SERIALIZER = RECIPE_SERIALIZERS.register("alloy_plant",
            () -> ALLOY_PLANT_RECIPE_SERIALIZER);

    public static final RegistryObject<RecipeSerializer<?>> ELECTRIC_CRUSHER_SERIALIZER = RECIPE_SERIALIZERS.register("electric_crusher",
            () -> ELECTRIC_CRUSHER_RECIPE_SERIALIZER);

    private static class TCRecipeType<T extends Recipe<?>> implements RecipeType<T> {
        @Override
        public String toString() {
            return Objects.requireNonNull(Registry.RECIPE_TYPE.getKey(this)).toString();
        }
    }

    private static <T extends RecipeType> T registerType(ResourceLocation recipeTypeId) {
        return (T) Registry.register(Registry.RECIPE_TYPE, recipeTypeId, new TCRecipeType<>());
    }
}