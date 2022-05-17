package com.tech0006.techcraft.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tech0006.techcraft.common.container.SafeContainer;
import com.tech0006.techcraft.techcraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;


public class SafeScreen extends AbstractContainerScreen<SafeContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(techcraft.MOD_ID, "textures/gui/safe.png");

    public SafeScreen(SafeContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 175;
        this.imageHeight = 183;
    }

    @Override
    public void render(PoseStack ms, final int mouseX, final int mouseY, final float partialTicks) {
        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
    }


    @Override
    protected void renderBg(PoseStack ms, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, TEXTURE);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(ms, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
    }
}
