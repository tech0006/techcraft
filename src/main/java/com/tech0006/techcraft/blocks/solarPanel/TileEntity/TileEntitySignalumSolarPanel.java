package com.tech0006.techcraft.blocks.solarPanel.TileEntity;

import com.tech0006.techcraft.blocks.solarPanel.SolarPanelLevel;
import com.tech0006.techcraft.init.ModTileEntityTypes;

public class TileEntitySignalumSolarPanel extends TileEntitySolarPanel {

    public TileEntitySignalumSolarPanel()
    {
        super(SolarPanelLevel.Signalum, ModTileEntityTypes.SIGNALUM_TILE.get());
    }
}
