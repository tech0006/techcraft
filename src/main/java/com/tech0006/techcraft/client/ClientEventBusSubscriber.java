package com.tech0006.techcraft.client;

import com.tech0006.techcraft.client.entity.render.ScientistRender;
import com.tech0006.techcraft.client.screen.*;
import com.tech0006.techcraft.common.registration.TCContainerTypes;
import com.tech0006.techcraft.common.registration.TCEntityTypes;
import com.tech0006.techcraft.techcraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = techcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    private ClientEventBusSubscriber() {
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(TCContainerTypes.SAFE.get(), SafeScreen::new);
        MenuScreens.register(TCContainerTypes.TC_BENCH.get(), TCbenchScreen::new);
        MenuScreens.register(TCContainerTypes.TC_FORGE.get(), TCforgeScreen::new);

        MenuScreens.register(TCContainerTypes.COAL_GENERATOR.get(), CoalGeneratorScreen::new);
        MenuScreens.register(TCContainerTypes.ELECTRIC_FURNACE.get(), ElectricFurnaceScreen::new);

        MenuScreens.register(TCContainerTypes.WIRE_SHAPER.get(), WireShaperScreen::new);
        MenuScreens.register(TCContainerTypes.ALLOY_PLANT.get(), AlloyPlantScreen::new);

        MenuScreens.register(TCContainerTypes.ELECTRIC_CRUSHER.get(), ElectricCrusherScreen::new);

    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {

    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(TCEntityTypes.SCIENTIST.get(), ScientistRender::new);
    }
}