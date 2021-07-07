package com.tech0006.techcraft.blocks.solarPanel;

import com.tech0006.techcraft.blocks.solarPanel.TileEntity.TileEntitySolarPanel;
import com.tech0006.techcraft.techcraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateSolarPanel {

    private BlockPos pos;
    private int currentEnergy;
    private int currentProduction;

    public UpdateSolarPanel(PacketBuffer buf)
    {
        pos = buf.readBlockPos();
        currentEnergy = buf.readInt();
        currentProduction = buf.readInt();
    }

    public UpdateSolarPanel(BlockPos pos, int currentEnergy, int currentProduction)
    {
        this.pos = pos;
        this.currentEnergy = currentEnergy;
        this.currentProduction = currentProduction;
    }

    public void toBytes(PacketBuffer buf)
    {
        buf.writeBlockPos(pos);
        buf.writeInt(currentEnergy);
        buf.writeInt(currentProduction);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() ->
        {
            World world = techcraft.proxy.getClientWorld();
            if(world.isBlockPresent(pos))
            {
                TileEntity te = world.getTileEntity(pos);
                if(te instanceof TileEntitySolarPanel)
                {
                    TileEntitySolarPanel solar = (TileEntitySolarPanel) te;
                    solar.energyClient = currentEnergy;
                    solar.energyProductionClient = currentProduction;
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
