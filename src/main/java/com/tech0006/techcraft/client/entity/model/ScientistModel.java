package com.tech0006.techcraft.client.entity.model;

import com.tech0006.techcraft.entity.NPC.Scientist;
import com.tech0006.techcraft.techcraft;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ScientistModel extends AnimatedGeoModel<Scientist>
{
    @Override
    public ResourceLocation getModelLocation(Scientist object)
    {
        return new ResourceLocation(techcraft.MOD_ID, "geo/entities/scientist.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Scientist object)
    {
        return new ResourceLocation(techcraft.MOD_ID, "textures/entity/scientist.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Scientist object)
    {
        return new ResourceLocation(techcraft.MOD_ID, "animations/scientist/scientist.json");
    }
}