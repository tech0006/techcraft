package com.tech0006.techcraft.GUI.Container.SolarPanel;

import com.tech0006.techcraft.init.ModContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SolarPanelHardenedContainer extends SolarPanelContainer {

    public SolarPanelHardenedContainer(int windowId, World world, BlockPos pos, PlayerEntity player)
    {
        super(ModContainerTypes.SOLAR_PANEL_HARDENED.get(), windowId, world, pos, player);
    }
}
