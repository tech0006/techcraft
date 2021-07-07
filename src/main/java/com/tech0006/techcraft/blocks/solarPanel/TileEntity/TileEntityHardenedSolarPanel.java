package com.tech0006.techcraft.blocks.solarPanel.TileEntity;

import com.tech0006.techcraft.blocks.solarPanel.SolarPanelLevel;
import com.tech0006.techcraft.init.ModTileEntityTypes;

public class TileEntityHardenedSolarPanel extends TileEntitySolarPanel {

    public TileEntityHardenedSolarPanel()
    {
        super(SolarPanelLevel.Hardened, ModTileEntityTypes.HARDENED_TILE.get());
    }
}