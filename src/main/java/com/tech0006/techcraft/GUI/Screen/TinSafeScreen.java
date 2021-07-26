package com.tech0006.techcraft.GUI.Screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tech0006.techcraft.GUI.Container.TinSafeContainer;
import com.tech0006.techcraft.techcraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TinSafeScreen extends ContainerScreen<TinSafeContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(techcraft.MOD_ID, "textures/gui/tin_safe.png");

    public TinSafeScreen(TinSafeContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 175;
        this.imageHeight = 183;
    }

    @Override
    public void render(MatrixStack ms, final int mouseX, final int mouseY, final float partialTicks) {
        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(MatrixStack ms, int mouseX, int mouseY) {
        //super.renderLabels(ms, mouseX, mouseY);
        this.font.draw(ms, this.title, 8.0f, 6.0f, 4210752);
        this.font.draw(ms, this.inventory.getDisplayName(), 8.0f, 90.0f, 4210752);
    }

    @Override
    protected void renderBg(MatrixStack ms, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bind(BACKGROUND_TEXTURE);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        this.blit(ms, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }
}
