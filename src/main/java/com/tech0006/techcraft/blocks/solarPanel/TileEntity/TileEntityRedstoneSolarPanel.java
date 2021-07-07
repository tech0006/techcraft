package com.tech0006.techcraft.blocks.solarPanel.TileEntity;

import com.tech0006.techcraft.blocks.solarPanel.SolarPanelLevel;
import com.tech0006.techcraft.init.ModTileEntityTypes;

public class TileEntityRedstoneSolarPanel extends TileEntitySolarPanel {

    public TileEntityRedstoneSolarPanel()
    {
        super(SolarPanelLevel.Redstone, ModTileEntityTypes.REDSTONE_TILE.get());
    }
}
