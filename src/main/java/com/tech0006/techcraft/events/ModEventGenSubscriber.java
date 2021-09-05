package com.tech0006.techcraft.events;

import com.tech0006.techcraft.init.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class ModEventGenSubscriber {


    /*@SubscribeEvent
    public static void onInitBiomesGen(FMLCommonSetupEvent event) {
        for (Biome biome : ForgeRegistries.BIOMES) {

            biome.addFeature(
                    GenerationStage.Decoration.UNDERGROUND_ORES,
                    Feature.ORE.configured(
                            new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                    Blocks.COPPER_ORE.get().defaultBlockState(),
                                    15))
                            .withPlacement(
                                    Placement.RANGE.configure(
                                            new TopSolidRangeConfig(20, 0, 0, 100)))
            );

            biome.addFeature(
                    GenerationStage.Decoration.UNDERGROUND_ORES,
                    Feature.ORE.configured(
                            new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                    Blocks.TIN_ORE.get().defaultBlockState(),
                                    10))
                            .withPlacement(
                                    Placement.COUNT_RANGE.configure(
                                            new CountRangeConfig(15, 0, 0, 100)))
            );

        }
    }*/
	
	public static void generateOres(final BiomeLoadingEvent event) {
		if (!(event.getCategory().equals(Biome.Category.NETHER) || event.getCategory().equals(Biome.Category.THEEND))) {
			generateOre(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE,
					Blocks.COPPER_ORE.get().defaultBlockState(), 5, 15, 30, 10);
			generateOre(event.getGeneration(), OreFeatureConfig.FillerBlockType.NATURAL_STONE,
					Blocks.TIN_ORE.get().defaultBlockState(), 5, 15, 30, 10);
		}
	}

	private static void generateOre(BiomeGenerationSettingsBuilder settings, RuleTest fillerType, BlockState state,
			int veinSize, int minHeight, int maxHeight, int amount) {
		settings.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
				Feature.ORE.configured(new OreFeatureConfig(fillerType, state, veinSize))
						.decorated(Placement.RANGE.configured(new TopSolidRangeConfig(minHeight, 0, maxHeight)))
						.squared().count(amount));
	}
}
