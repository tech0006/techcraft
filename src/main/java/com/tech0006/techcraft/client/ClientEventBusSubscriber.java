package com.tech0006.techcraft.client;

import com.tech0006.techcraft.client.entity.render.ScientistRender;
import com.tech0006.techcraft.client.screen.*;
import com.tech0006.techcraft.common.registration.TCContainerTypes;
import com.tech0006.techcraft.common.registration.TCEntityTypes;
import com.tech0006.techcraft.techcraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = techcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        ScreenManager.register(TCContainerTypes.SAFE.get(), SafeScreen::new);
        ScreenManager.register(TCContainerTypes.TC_BENCH.get(), TCbenchScreen::new);
        ScreenManager.register(TCContainerTypes.TC_FORGE.get(), TCforgeScreen::new);

        ScreenManager.register(TCContainerTypes.COAL_GENERATOR.get(), CoalGeneratorScreen::new);
        ScreenManager.register(TCContainerTypes.ELECTRIC_FURNACE.get(), ElectricFurnaceScreen::new);

        ScreenManager.register(TCContainerTypes.WIRE_SHAPER.get(), WireShaperScreen::new);
        ScreenManager.register(TCContainerTypes.ALLOY_PLANT.get(), AlloyPlantScreen::new);

        ScreenManager.register(TCContainerTypes.ELECTRIC_CRUSHER.get(), ElectricCrusherScreen::new);


        RenderingRegistry.registerEntityRenderingHandler(TCEntityTypes.SCIENTIST.get(), ScientistRender::new);

    }
}