package com.tech0006.techcraft.blocks.solarPanel.TileEntity;

import com.tech0006.techcraft.blocks.solarPanel.SolarPanelLevel;
import com.tech0006.techcraft.init.ModTileEntityTypes;

public class TileEntityLeadstoneSolarPanel extends TileEntitySolarPanel {

    public TileEntityLeadstoneSolarPanel()
    {
        super(SolarPanelLevel.Leadstone, ModTileEntityTypes.LEADSTONE_TILE.get());
    }
}
