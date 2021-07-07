package com.tech0006.techcraft.events;

import com.tech0006.techcraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEventGenSubscriber {


    @SubscribeEvent
    public static void onInitBiomesGen(FMLCommonSetupEvent event) {
        for (Biome biome : ForgeRegistries.BIOMES) {

            biome.addFeature(
                    GenerationStage.Decoration.UNDERGROUND_ORES,
                    Feature.ORE.withConfiguration(
                            new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                    Blocks.COPPER_ORE.get().getDefaultState(),
                                    15))
                            .withPlacement(
                                    Placement.COUNT_RANGE.configure(
                                            new CountRangeConfig(20, 0, 0, 100)))
            );

            biome.addFeature(
                    GenerationStage.Decoration.UNDERGROUND_ORES,
                    Feature.ORE.withConfiguration(
                            new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                    Blocks.TIN_ORE.get().getDefaultState(),
                                    10))
                            .withPlacement(
                                    Placement.COUNT_RANGE.configure(
                                            new CountRangeConfig(15, 0, 0, 100)))
            );

        }
    }
}
