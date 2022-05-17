//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tech0006.techcraft.world.generation;

import com.tech0006.techcraft.world.structure.Structures;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class StructureGeneration {
    public static void generateStructures(BiomeLoadingEvent event) {
        ResourceKey<Biome> key = ResourceKey.create(Registry.BIOME_REGISTRY, Objects.requireNonNull(event.getName()));
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);
        if (types.contains(Type.PLAINS)) {
            List<Supplier<ConfiguredStructureFeature<?, ?>>> structures = event.getGeneration().getStructures();
            structures.add(() -> {
                return ((StructureFeature)Structures.SCIENTIST_HOUSE.get()).configured(FeatureConfiguration.NONE);
            });
        }

    }
}
