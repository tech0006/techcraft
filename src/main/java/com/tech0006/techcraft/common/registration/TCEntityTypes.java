package com.tech0006.techcraft.common.registration;

import com.tech0006.techcraft.entity.NPC.Scientist;
import com.tech0006.techcraft.techcraft;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TCEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, techcraft.MOD_ID);

    public static final RegistryObject<EntityType<Scientist>> SCIENTIST = ENTITY_TYPES.register("scientist",
            () -> EntityType.Builder.of(Scientist::new, EntityClassification.AMBIENT)
                    .sized(0.5625f, 2.0f)
                    .build("scientist"));



}
