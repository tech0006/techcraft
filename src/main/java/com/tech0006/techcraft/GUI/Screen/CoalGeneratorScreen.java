package com.tech0006.techcraft.GUI.Screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tech0006.techcraft.GUI.Container.CoalGeneratorContainer;
import com.tech0006.techcraft.blocks.TileEntity.CoalGeneratorTileEntity;
import com.tech0006.techcraft.techcraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class CoalGeneratorScreen extends ContainerScreen<CoalGeneratorContainer> {

    private static final ResourceLocation TEXTURES = new ResourceLocation(techcraft.MOD_ID, "textures/gui/coal_generator.png");
    private final CoalGeneratorTileEntity tile;
    private int currEn = 0;

    public CoalGeneratorScreen(CoalGeneratorContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
        this.tile = container.tile;
    }

    @Override
    public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
        if (mouseX > leftPos + 7 && mouseX < leftPos + 29 && mouseY > topPos + 10 && mouseY < topPos + 77)
            this.renderTooltip(ms, new StringTextComponent("Energy: " + getPercent() + "%  " + getEnergyFormatted(tile.getEnergyStored()) + "/" + getEnergyFormatted(tile.getMaxEnergy())), mouseX, mouseY);
    }

    @Override
    protected void renderLabels(MatrixStack ms, int mouseX, int mouseY) {


        String generation = "Generation: " + (-tile.currentAmountEnergyProduced()) + " FE/t";
        this.font.draw(ms, generation, (imageWidth / 2 - font.width(generation) / 2) + 14, 55, 4210752);
    }

    @Override
    protected void renderBg(MatrixStack ms, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bind(TEXTURES);
        this.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

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
        Long currentEnergy = new Long(tile.getEnergyStored());
        int maxEnergy = tile.getMaxEnergy();

        long result = currentEnergy * 100 / maxEnergy;

        return (int) result;
    }
}