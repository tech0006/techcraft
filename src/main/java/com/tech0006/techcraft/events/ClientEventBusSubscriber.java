package com.tech0006.techcraft.events;

import com.tech0006.techcraft.GUI.Screen.*;
import com.tech0006.techcraft.init.ModContainerTypes;
import com.tech0006.techcraft.techcraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = techcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(ModContainerTypes.COPPER_SAFE.get(), CopperSafeScreen::new);
        ScreenManager.registerFactory(ModContainerTypes.TIN_SAFE.get(), TinSafeScreen::new);
        ScreenManager.registerFactory(ModContainerTypes.BRONZE_SAFE.get(), BronzeSafeScreen::new);
        ScreenManager.registerFactory(ModContainerTypes.IRON_SAFE.get(), IronSafeScreen::new);
        ScreenManager.registerFactory(ModContainerTypes.GOLD_SAFE.get(), GoldSafeScreen::new);
        ScreenManager.registerFactory(ModContainerTypes.TC_BENCH.get(), TCbenchScreen::new);
        ScreenManager.registerFactory(ModContainerTypes.TC_FORGE.get(), TCforgeScreen::new);

        ScreenManager.registerFactory(ModContainerTypes.SOLAR_PANEL_LEADSTONE.get(), SolarPanelScreen::new);
        ScreenManager.registerFactory(ModContainerTypes.SOLAR_PANEL_HARDENED.get(), SolarPanelScreen::new);
        ScreenManager.registerFactory(ModContainerTypes.SOLAR_PANEL_REDSTONE.get(), SolarPanelScreen::new);
        ScreenManager.registerFactory(ModContainerTypes.SOLAR_PANEL_SIGNALUM.get(), SolarPanelScreen::new);
        ScreenManager.registerFactory(ModContainerTypes.SOLAR_PANEL_RESONANT.get(), SolarPanelScreen::new);
        ScreenManager.registerFactory(ModContainerTypes.SOLAR_PANEL_ADVANCED.get(), SolarPanelScreen::new);
        ScreenManager.registerFactory(ModContainerTypes.SOLAR_PANEL_ULTIMATE.get(), SolarPanelScreen::new);

        ScreenManager.registerFactory(ModContainerTypes.COAL_GENERATOR.get(), CoalGeneratorScreen::new);
    }
}