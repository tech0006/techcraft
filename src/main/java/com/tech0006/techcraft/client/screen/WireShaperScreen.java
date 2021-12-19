package com.tech0006.techcraft.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tech0006.techcraft.common.container.WireShaperContainer;
import com.tech0006.techcraft.techcraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;


public class WireShaperScreen extends ContainerScreen<WireShaperContainer> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(techcraft.MOD_ID,
            "textures/gui/wire_shaper.png");

    public WireShaperScreen(WireShaperContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(MatrixStack ms, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        assert this.minecraft != null;
        this.minecraft.getTextureManager().bind(TEXTURE);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        this.blit(ms, x, y, 0, 0, this.imageWidth, this.imageHeight);

    }

    @Override
    protected void renderLabels(MatrixStack ms, int mouseX, int mouseY) {
        //super.renderLabels(ms ,mouseX, mouseY);
        this.font.draw(ms, this.title, 8.0f, 5.0f, 0x404040);
        this.font.draw(ms, this.inventory.getDisplayName(), 8.0f, 70.0f, 0x404040);
    }

    @Override
    public void render(MatrixStack ms, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms ,mouseX, mouseY);
    }
}
