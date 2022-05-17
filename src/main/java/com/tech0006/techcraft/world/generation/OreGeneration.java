//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tech0006.techcraft.world.generation;

import com.tech0006.techcraft.common.registration.TCBlocks;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.tech0006.techcraft.techcraft;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.Predicates;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

public class OreGeneration {
	public static final List<ConfiguredFeature<?, ?>> OVERWORLD_ORES = new ArrayList();
	public static final List<ConfiguredFeature<?, ?>> END_ORES = new ArrayList();
	public static final List<ConfiguredFeature<?, ?>> NETHER_ORES = new ArrayList();

	public OreGeneration() {
	}

	public static void registerOres() {
		registerOre(Predicates.STONE_ORE_REPLACEABLES, TCBlocks.TIN_ORE.get().defaultBlockState(), 2, 5, 100, 2, "tin_ore");
	}

	public static void registerOre(RuleTest predicate, BlockState state, int veinSize, int minHeight, int maxHeight, int amount, String name) {
		ConfiguredFeature<?, ?> ore = Feature.ORE.configured(new OreConfiguration(List.of(
				OreConfiguration.target(predicate, state)), veinSize)).rangeUniform(VerticalAnchor.aboveBottom(minHeight),
				VerticalAnchor.aboveBottom(maxHeight)).squared().count(amount);
		OVERWORLD_ORES.add(register(name, ore));
	}

	private static <Config extends FeatureConfiguration> ConfiguredFeature<Config, ?> register(String name, ConfiguredFeature<Config, ?> configuredFeature) {
			return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(techcraft.MOD_ID, name),
					configuredFeature);
	}

	@EventBusSubscriber(modid = "techcraft", bus = Bus.FORGE)
	public static class ForgeBusSubscriber {
		public ForgeBusSubscriber() {
		}

		@SubscribeEvent
		public static void biomeLoading(BiomeLoadingEvent event) {
			List<Supplier<ConfiguredFeature<?, ?>>> features = event.getGeneration().getFeatures(Decoration.UNDERGROUND_ORES);
			switch (event.getCategory()) {
				case NETHER:
					OreGeneration.NETHER_ORES.forEach((ore) -> {
						features.add(() -> {
							return ore;
						});
					});
					break;
				case THEEND:
					OreGeneration.END_ORES.forEach((ore) -> {
						features.add(() -> {
							return ore;
						});
					});
					break;
				default:
					OreGeneration.OVERWORLD_ORES.forEach((ore) -> {
						features.add(() -> {
							return ore;
						});
					});
			}

		}
	}
}
