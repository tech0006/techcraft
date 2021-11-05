package com.tech0006.techcraft.util.tools;

import net.minecraft.util.RegistryKey;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class WorldUtil {

    public static boolean isWorld(IServerWorld world, RegistryKey<World>... keys) {
        for (RegistryKey<World> key : keys) {
            if (world.getLevel().dimension() == key)
                return true;
        }

        return false;
    }

    public static boolean isWorld(World world, RegistryKey<World>... keys) {
        for (RegistryKey<World> key : keys) {
            if (world.dimension() == key)
                return true;
        }

        return false;
    }

}
