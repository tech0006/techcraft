package com.tech0006.techcraft.blocks.solarPanel.TileEntity;

import com.tech0006.techcraft.blocks.solarPanel.SolarPanelLevel;
import com.tech0006.techcraft.init.ModTileEntityTypes;

public class TileEntityResonantSolarPanel extends TileEntitySolarPanel {

    public TileEntityResonantSolarPanel()
    {
        super(SolarPanelLevel.Resonant, ModTileEntityTypes.RESONANT_TILE.get());
    }
}
