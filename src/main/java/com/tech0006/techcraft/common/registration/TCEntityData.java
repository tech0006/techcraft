package com.tech0006.techcraft.common.registration;

import com.tech0006.techcraft.techcraft;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class TCEntityData {

    public static void init() {
        SpawnConditions.registerSpawnHeightLimitations();
        SpawnConditions.registerDaylightMobs();
    }

    public static class SpawnConditions {
        public static final HashMap<EntityType<?>, Integer> SPAWN_HEIGHTS = new HashMap<EntityType<?>, Integer>();
        public static final HashSet<EntityType<?>> DAYLIGHT_MOBS = new HashSet<EntityType<?>>();

        private static void registerSpawnHeightLimitations() {}

        private static void registerDaylightMobs() {
            DAYLIGHT_MOBS.addAll(Arrays.asList());
        }
    }

    @Mod.EventBusSubscriber(modid = techcraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    private static class Stats {
        @SubscribeEvent
        public static void registerStats(EntityAttributeCreationEvent ev) {
            /*AttributeBuilder.create(AoAEntities.Animals.AMBIENT_PIXON.get())
                    .health(15)
                    .moveSpeed(0.4)
                    .followRange(16)
                    .extraAttributes(
                            ForgeMod.SWIM_SPEED.get()).build(ev);*/
            AttributeBuilder.create(TCEntityTypes.SCIENTIST.get())
                    .health(100)
                    .moveSpeed(0.5)
                    .followRange(16)
                    .extraAttributes(
                            ForgeMod.SWIM_SPEED.get()).build(ev);
            }

        private static class AttributeBuilder {
            private final EntityType<? extends LivingEntity> entityType;
            private final AttributeSupplier.Builder attributeMap;

            private AttributeBuilder(EntityType<? extends LivingEntity> entityType, AttributeSupplier.Builder attributeMap) {
                this.entityType = entityType;
                this.attributeMap = attributeMap;
            }

            private static AttributeBuilder create(EntityType<? extends LivingEntity> entityType) {
                return new AttributeBuilder(entityType, LivingEntity.createLivingAttributes());
            }

            private static AttributeBuilder createMob(EntityType<? extends LivingEntity> entityType) {
                return new AttributeBuilder(entityType, Mob.createMobAttributes());
            }

            private AttributeBuilder health(double health) {
                attributeMap.add(Attributes.MAX_HEALTH, health);
                return this;
            }

            private AttributeBuilder swimSpeed(double speed) {
                attributeMap.add(ForgeMod.SWIM_SPEED.get(), speed);
                return this;
            }

            private AttributeBuilder moveSpeed(double speed) {
                attributeMap.add(Attributes.MOVEMENT_SPEED, speed);
                return this;
            }

            private AttributeBuilder followRange(double distance) {
                attributeMap.add(Attributes.FOLLOW_RANGE, distance);
                return this;
            }

            private AttributeBuilder extraAttributes(Attribute... attributes) {
                for (Attribute attribute : attributes) {
                    attributeMap.add(attribute);
                }
                return this;
            }

            private void build(EntityAttributeCreationEvent ev) {ev.put(entityType, attributeMap.build());}
        }
    }
}
