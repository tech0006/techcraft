package com.tech0006.techcraft.blocks.solarPanel.TileEntity;

import com.tech0006.techcraft.blocks.solarPanel.SolarPanelLevel;
import com.tech0006.techcraft.init.ModTileEntityTypes;

public class TileEntityAdvancedSolarPanel extends TileEntitySolarPanel {

    public TileEntityAdvancedSolarPanel()
    {
        super(SolarPanelLevel.Advanced, ModTileEntityTypes.ADVANCED_TILE.get());
    }
}
