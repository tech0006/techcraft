package com.tech0006.techcraft.util.handler;

import com.tech0006.techcraft.blocks.coalGenerator.UpdateCoalGenerator;
import com.tech0006.techcraft.techcraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;

public class PacketHandler {

    private static final String PROTOCOL_VERSION = "1";
    // @formatter:off
    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(techcraft.MOD_ID, "net"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);
    // @formatter:on
    public static void init()
    {
        int id = 0;
        INSTANCE.registerMessage(id++,
                UpdateCoalGenerator.class,
                UpdateCoalGenerator::toBytes,
                UpdateCoalGenerator::new,
                UpdateCoalGenerator::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    };
}