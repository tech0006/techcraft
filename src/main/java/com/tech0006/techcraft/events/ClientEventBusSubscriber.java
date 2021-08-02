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
        ScreenManager.register(ModContainerTypes.COPPER_SAFE.get(), CopperSafeScreen::new);
        ScreenManager.register(ModContainerTypes.TIN_SAFE.get(), TinSafeScreen::new);
        ScreenManager.register(ModContainerTypes.BRONZE_SAFE.get(), BronzeSafeScreen::new);
        ScreenManager.register(ModContainerTypes.IRON_SAFE.get(), IronSafeScreen::new);
        ScreenManager.register(ModContainerTypes.GOLD_SAFE.get(), GoldSafeScreen::new);
        ScreenManager.register(ModContainerTypes.TC_BENCH.get(), TCbenchScreen::new);
        ScreenManager.register(ModContainerTypes.TC_FORGE.get(), TCforgeScreen::new);

        ScreenManager.register(ModContainerTypes.COAL_GENERATOR.get(), CoalGeneratorScreen::new);
        ScreenManager.register(ModContainerTypes.ELECTRIC_FURNACE.get(), ElectricFurnaceScreen::new);

        ScreenManager.register(ModContainerTypes.WIRE_SHAPER.get(), WireShaperScreen::new);
        ScreenManager.register(ModContainerTypes.ALLOY_PLANT.get(), AlloyPlantScreen::new);
    }
}