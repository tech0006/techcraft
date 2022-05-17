package com.tech0006.techcraft.common.packet;

import com.tech0006.techcraft.blocks.blockentity.update.*;
import com.tech0006.techcraft.techcraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

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
    public static void init() {
        int id = 0;
        INSTANCE.registerMessage(id++,
                UpdateCoalGenerator.class,
                UpdateCoalGenerator::toBytes,
                UpdateCoalGenerator::new,
                UpdateCoalGenerator::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        INSTANCE.registerMessage(id++,
                UpdateElectricFurnace.class,
                UpdateElectricFurnace::toBytes,
                UpdateElectricFurnace::new,
                UpdateElectricFurnace::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        INSTANCE.registerMessage(id++,
                UpdateAlloyPlant.class,
                UpdateAlloyPlant::toBytes,
                UpdateAlloyPlant::new,
                UpdateAlloyPlant::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        INSTANCE.registerMessage(id++,
                UpdateElectricCrusher.class,
                UpdateElectricCrusher::toBytes,
                UpdateElectricCrusher::new,
                UpdateElectricCrusher::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        INSTANCE.registerMessage(id++,
                UpdateEnergyPipe.class,
                UpdateEnergyPipe::toBytes,
                UpdateEnergyPipe::new,
                UpdateEnergyPipe::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }
}