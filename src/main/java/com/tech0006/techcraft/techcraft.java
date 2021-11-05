package com.tech0006.techcraft;

import com.tech0006.techcraft.init.*;
import com.tech0006.techcraft.util.handler.PacketHandler;
import com.tech0006.techcraft.util.proxy.IProxy;
import com.tech0006.techcraft.util.proxy.Proxy;
import com.tech0006.techcraft.util.proxy.ProxyClient;
import com.tech0006.techcraft.world.structure.Structures;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod("techcraft")
public class techcraft {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "techcraft";
    public static IProxy proxy = DistExecutor.safeRunForDist(() -> ProxyClient::new, () -> Proxy::new);


    public techcraft() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        eventBus.addListener(this::doClientStuff);

        GeckoLib.initialize();

        Blocks.BLOCKS.register(eventBus);
        Items.ITEMS.register(eventBus);
        Structures.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);
        ModTileEntityTypes.TILE_ENTITY_TYPES.register(eventBus);
        ModContainerTypes.CONTAINER_TYPES.register(eventBus);
        ModEntityTypes.ENTITY_TYPES.register(eventBus);

        RecipeSerializerInit.RECIPE_SERIALIZERS.register(eventBus);
        MinecraftForge.EVENT_BUS.register(this);
        PacketHandler.init();

    }

    private void setup(final FMLCommonSetupEvent event) {
        Structures.setupStructures();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

}
