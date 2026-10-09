package net.ona.mlpfrien.ponyvisuals.animation;

import net.minecraft.client.animation.AnimationDefinition;
import net.ona.mlpfrien.ponyvisuals.models.PonyAnimation;

public enum PonyAnimationLogic {
    IDLE(PonyAnimation.idle, 1.0F),
    JUMP(PonyAnimation.jump, 1.0F),
    LAND(PonyAnimation.land,1.0f),
    FALL(PonyAnimation.fall, 3.0F),
    CROUCH(PonyAnimation.crouch, 2.0f),
    FLY(PonyAnimation.flight,3.0f);

    private final AnimationDefinition definition;
    private final float transitionTicks;

    PonyAnimationLogic(AnimationDefinition definition, float transitionTicks) {
        this.definition = definition;
        this.transitionTicks = transitionTicks;
    }

    public AnimationDefinition definition() {
        return definition;
    }

    public float transitionTicks() {
        return transitionTicks;
    }
}
