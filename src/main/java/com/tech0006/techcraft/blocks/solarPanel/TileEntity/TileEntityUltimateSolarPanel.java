package com.tech0006.techcraft.blocks.solarPanel.TileEntity;

import com.tech0006.techcraft.blocks.solarPanel.SolarPanelLevel;
import com.tech0006.techcraft.init.ModTileEntityTypes;

public class TileEntityUltimateSolarPanel extends TileEntitySolarPanel {

    public TileEntityUltimateSolarPanel()
    {
        super(SolarPanelLevel.Ultimate, ModTileEntityTypes.ULTIMATE_TILE.get());
    }
}
