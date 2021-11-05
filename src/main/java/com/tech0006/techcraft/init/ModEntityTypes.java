package com.tech0006.techcraft.init;

import com.tech0006.techcraft.entity.Scientist;
import com.tech0006.techcraft.techcraft;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, techcraft.MOD_ID);

    public static final RegistryObject<EntityType<Scientist>> SCIENTIST = ENTITY_TYPES.register("scientist",
            () -> EntityType.Builder.of(Scientist::new, EntityClassification.AMBIENT)
                    .sized(0.5625f, 2.0f)
                    .build("scientist"));



}
