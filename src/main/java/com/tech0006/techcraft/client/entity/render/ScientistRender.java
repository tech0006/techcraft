package com.tech0006.techcraft.client.entity.render;

import com.tech0006.techcraft.client.entity.model.ScientistModel;
import com.tech0006.techcraft.entity.NPC.Scientist;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ScientistRender extends GeoEntityRenderer<Scientist>
{
    public ScientistRender(EntityRendererProvider.Context renderManager)
    {
        super(renderManager, new ScientistModel());
        this.shadowStrength = 0.7F; //change 0.7 to the desired shadow size.
    }
}
