package com.tech0006.techcraft.GUI.Screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tech0006.techcraft.GUI.Container.AlloyPlantContainer;
import com.tech0006.techcraft.blocks.TileEntity.AlloyPlantTileEntity;
import com.tech0006.techcraft.techcraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.Collections;

public class AlloyPlantScreen extends ContainerScreen<AlloyPlantContainer> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(techcraft.MOD_ID, "textures/gui/alloy_plant.png");
    private final AlloyPlantTileEntity tile;

    public AlloyPlantScreen(AlloyPlantContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
        this.tile = container.tileEntity;
    }

    @Override
    public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
        if (mouseX > leftPos + 7 && mouseX < leftPos + 29 && mouseY > topPos + 10 && mouseY < topPos + 77)
            this.renderTooltip(ms, new StringTextComponent("Lava: " + getPercent() + "%  " + tile.getFluid() + "/" + tile.getFluidMax()), mouseX, mouseY);
    }

    @Override
    protected void renderLabels(MatrixStack ms, int mouseX, int mouseY) {
    }

    @Override
    protected void renderBg(MatrixStack ms, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bind(TEXTURES);
        this.blit(ms ,this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        // Energy
        int y = this.getEnergyScaled(60);
        this.blit(ms ,this.leftPos + 10, this.topPos + 12 + y, 176, 0, 16, 60 - y);

        // Render arrow
        int l = this.menu.getProgressScaled(24);
        this.blit(ms ,72 + leftPos, 32 + topPos, 176, 60, l + 1, 18);
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