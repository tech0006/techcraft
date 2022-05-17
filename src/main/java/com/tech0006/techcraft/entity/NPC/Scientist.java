package com.tech0006.techcraft.entity.NPC;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class Scientist extends PathfinderMob implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private static final AnimationBuilder WALK_ANIMATION = new AnimationBuilder().addAnimation(
            "animation.techcraft.scientist.walk", true);

    public Scientist(EntityType<? extends PathfinderMob> type, Level levelIn)
    {
        super(type, levelIn);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new OpenDoorGoal(this, true));
        goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 3f, 1f));
        goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(new AnimationController<>(this, "base_animations",
                0, new AnimationController.IAnimationPredicate<Scientist>() {
            @Override
            public PlayState test(AnimationEvent<Scientist> event) {
                if (event.isMoving()) {
                    event.getController().setAnimation(WALK_ANIMATION);
                    return PlayState.CONTINUE;
                }
                return PlayState.STOP;
            }
        }));
    }

    @Override
    public AnimationFactory getFactory() {return this.factory;}
}
