package com.tech0006.techcraft.GUI.Screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tech0006.techcraft.GUI.Container.ElectricFurnaceContainer;
import com.tech0006.techcraft.blocks.TileEntity.ElectricFurnaceTileEntity;
import com.tech0006.techcraft.techcraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.Collections;

public class ElectricFurnaceScreen extends ContainerScreen<ElectricFurnaceContainer> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(techcraft.MOD_ID, "textures/gui/electric_furnace.png");
    private final ElectricFurnaceTileEntity tile;

    public ElectricFurnaceScreen(ElectricFurnaceContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
        this.tile = container.tile;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        if (mouseX > guiLeft + 7 && mouseX < guiLeft + 29 && mouseY > guiTop + 10 && mouseY < guiTop + 77)
            this.renderTooltip(Collections.singletonList("Energy: " + getPercent() + "%  " + getEnergyFormatted(tile.getEnergyStored()) + "/" + getEnergyFormatted(tile.getMaxEnergy())), mouseX, mouseY, font);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String generation = "Energy consumption: " + getEnergyFormatted(tile.getCost()) + "/t";
        this.font.drawString(generation, (xSize / 2 - 10 - font.getStringWidth(generation) / 2) + 14, 65, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(TEXTURES);
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        // Energy
        int y = this.getEnergyScaled(60);
        this.blit(this.guiLeft + 10, this.guiTop + 12 + y, 176, 0, 16, 60 - y);

        // Render arrow
        int l = this.container.getProgressScaled(24);
        this.blit(74 + guiLeft, 29 + guiTop, 176, 60, l + 1, 18);
    }

    private String getEnergyFormatted(int energy) {
        if (energy >= 1000000)
            return (energy / 1000) + " kFE";
        else
            return energy + " FE";
    }

    private int getEnergyScaled(int pixels) {
        return pixels - (pixels * getPercent() / 100);
    }

    private int getPercent() {
        Long currentEnergy = new Long(tile.getEnergyStored());
        int maxEnergy = tile.getMaxEnergy();

        long result = currentEnergy * 100 / maxEnergy;

        return (int) result;
    }
}