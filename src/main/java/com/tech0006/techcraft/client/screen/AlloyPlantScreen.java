package com.tech0006.techcraft.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tech0006.techcraft.blocks.blockentity.functional.AlloyPlantBlockEntity;
import com.tech0006.techcraft.common.container.AlloyPlantContainer;
import com.tech0006.techcraft.techcraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;


public class AlloyPlantScreen extends AbstractContainerScreen<AlloyPlantContainer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(techcraft.MOD_ID, "textures/gui/alloy_plant.png");
    private final AlloyPlantBlockEntity tile;

    public AlloyPlantScreen(AlloyPlantContainer container, Inventory inv, Component name) {
        super(container, inv, name);
        this.tile = container.blockEntity;
    }

    @Override
    public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
        if (mouseX > leftPos + 7 && mouseX < leftPos + 29 && mouseY > topPos + 10 && mouseY < topPos + 77)
            this.renderTooltip(ms, new TextComponent("Lava: " + getPercent() + "%  " + tile.getFluid() + "/" + tile.getFluidMax()), mouseX, mouseY);

    }

    @Override
    protected void renderLabels(PoseStack ms, int mouseX, int mouseY) {
    }

    @Override
    protected void renderBg(PoseStack ms, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, TEXTURE);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(ms, relX, relY, 0, 0, this.imageWidth, this.imageHeight);

        // Energy
        int y = this.getEnergyScaled(60);
        this.blit(ms ,relX + 10, relY + 12 + y, 176, 0, 16, 60 - y);

        // Render arrow
        int l = this.menu.getProgressScaled(24);
        this.blit(ms ,72 + relX, 32 + relY, 176, 60, l + 1, 18);
    }

    private int getEnergyScaled(int pixels) {
        return pixels - (pixels * getPercent() / 100);
    }

    private int getPercent() {
        long currentEnergy = tile.getFluid();
        int maxEnergy = tile.getFluidMax();

        long result = currentEnergy * 100 / maxEnergy;

        return (int) result;
    }
}