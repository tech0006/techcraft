package com.tech0006.techcraft.GUI.Screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tech0006.techcraft.GUI.Container.AlloyPlantContainer;
import com.tech0006.techcraft.blocks.TileEntity.AlloyPlantTileEntity;
import com.tech0006.techcraft.techcraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.Collections;

public class AlloyPlantScreen extends ContainerScreen<AlloyPlantContainer> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(techcraft.MOD_ID, "textures/gui/alloy_plant.png");
    private final AlloyPlantTileEntity tile;

    public AlloyPlantScreen(AlloyPlantContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
        this.tile = container.tileEntity;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        if (mouseX > guiLeft + 7 && mouseX < guiLeft + 29 && mouseY > guiTop + 10 && mouseY < guiTop + 77)
            this.renderTooltip(Collections.singletonList("Fluid: " + getPercent() + "%  " + tile.getFluid() + "/" + tile.getFluidMax()), mouseX, mouseY, font);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
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
        this.blit(72 + guiLeft, 32 + guiTop, 176, 60, l + 1, 18);
    }

    private int getEnergyScaled(int pixels) {
        return pixels - (pixels * getPercent() / 100);
    }

    private int getPercent() {
        Long currentEnergy = new Long(tile.getFluid());
        int maxEnergy = tile.getFluidMax();

        long result = currentEnergy * 100 / maxEnergy;

        return (int) result;
    }
}
