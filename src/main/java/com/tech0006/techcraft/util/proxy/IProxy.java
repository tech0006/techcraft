package com.tech0006.techcraft.util.proxy;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface IProxy {

    Player getClientPlayer();

    Level getClientWorld();
}