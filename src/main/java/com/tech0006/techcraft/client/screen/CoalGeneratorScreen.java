package com.tech0006.techcraft.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tech0006.techcraft.blocks.blockentity.functional.CoalGeneratorBlockEntity;
import com.tech0006.techcraft.common.container.CoalGeneratorContainer;
import com.tech0006.techcraft.techcraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CoalGeneratorScreen extends AbstractContainerScreen<CoalGeneratorContainer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(techcraft.MOD_ID, "textures/gui/coal_generator.png");
    private final CoalGeneratorBlockEntity tile;

    public CoalGeneratorScreen(CoalGeneratorContainer container, Inventory inv, Component name) {
        super(container, inv, name);
        this.tile = container.blockEntity;
    }

    @Override
    public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
        if (mouseX > leftPos + 7 && mouseX < leftPos + 29 && mouseY > topPos + 10 && mouseY < topPos + 77)
            this.renderTooltip(ms, new TextComponent("Energy: " + getPercent() + "%  " + getEnergyFormatted(tile.getEnergyStored()) + "/" + getEnergyFormatted(tile.getMaxEnergy())), mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack ms, int mouseX, int mouseY) {


        String generation = "Generation: " + (-tile.currentAmountEnergyProduced()) + " FE/t";
        this.font.draw(ms, generation, (imageWidth / 2 - font.width(generation) / 2) + 14, 55, 4210752);
    }

    @Override
    protected void renderBg(PoseStack ms, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, TEXTURE);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(ms, relX, relY, 0, 0, this.imageWidth, this.imageHeight);

        // Energy
        int y = this.getEnergyScaled(60);
        this.blit(ms, this.leftPos + 10, this.topPos + 12 + y, 176, 0, 16, 60 - y);

        // Render burning flame
        if (this.menu.isBurning()) {
            int l = this.menu.getBurnTimeScaled();
            this.blit(ms, 35 + this.leftPos, 14 + this.topPos + 12 - l, 176, 72 - l, 14, l + 1);
        }
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
        long currentEnergy = tile.getEnergyStored();
        int maxEnergy = tile.getMaxEnergy();

        long result = currentEnergy * 100 / maxEnergy;

        return (int) result;
    }
}