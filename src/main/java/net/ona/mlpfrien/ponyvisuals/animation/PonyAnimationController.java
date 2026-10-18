package net.ona.mlpfrien.ponyvisuals.animation;

import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import org.joml.Vector3f;

public class PonyAnimationController {
    private PonyAnimationLogic current;
    private PonyAnimationLogic previous;
    private float currentStartTime;
    private float previousStartTime;
    private float transitionStartTime;
    private float transitionDuration;
    private boolean initialized = false;

    public void transitionTo(PonyAnimationLogic next, float ageInTicks) {
        if (!initialized) {
            initialized = true;

            current = next;
            currentStartTime = ageInTicks;

            previous = null;
            return;
        }
        if (next == current) {
            return;
        }
        previous = current;
        previousStartTime = currentStartTime;

        current = next;
        currentStartTime = ageInTicks;

        transitionStartTime = ageInTicks;
        transitionDuration = next.transitionTicks();
    }


    public void apply(HierarchicalModel<?> model, float ageInTicks, Vector3f animationCache) {
        if (!initialized || current == null) {
            return;
        }
        if (previous == null) {
            animate(model, current, currentStartTime, ageInTicks, 1.0F, animationCache);
            return;
        }
        float progress;

        if (transitionDuration <= 0.0F) {
            progress = 1.0F;
        } else {
            progress = (ageInTicks - transitionStartTime) / transitionDuration;
        }
        progress = Math.clamp(progress, 0.0F, 1.0F);
        float blend = progress * progress * (3.0F - 2.0F * progress);

        animate(model, previous, previousStartTime, ageInTicks, 1.0F - blend, animationCache);
        animate(model, current, currentStartTime, ageInTicks, blend, animationCache);
        if (progress >= 1.0F) {
            previous = null;
        }
    }


    private void animate(HierarchicalModel<?> model, PonyAnimationLogic animation, float startTime, float ageInTicks, float weight, Vector3f animationCache) {
        long elapsedMilliseconds = (long) ((ageInTicks - startTime) * 50.0F);
        KeyframeAnimations.animate(model, animation.definition(), elapsedMilliseconds, weight, animationCache);
    }

    public PonyAnimationLogic getCurrent() {
        return current;
    }

    public boolean isTransitioning() {
        return previous != null;
    }
}
