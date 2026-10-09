package net.ona.mlpfrien.ponyvisuals.animation;

import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import org.joml.Vector3f;

public class PonyAnimationController {

    private PonyAnimationLogic current;
    private PonyAnimationLogic previous;

    // ageInTicks at which each animation started
    private float currentStartTime;
    private float previousStartTime;

    // transition timing
    private float transitionStartTime;
    private float transitionDuration;

    private boolean initialized = false;


    public void transitionTo(
            PonyAnimationLogic next,
            float ageInTicks
    ) {
        // First animation ever
        if (!initialized) {
            initialized = true;

            current = next;
            currentStartTime = ageInTicks;

            previous = null;

            return;
        }

        // Already playing it
        if (next == current) {
            return;
        }

        // Current becomes previous
        previous = current;
        previousStartTime = currentStartTime;

        // New animation becomes current
        current = next;
        currentStartTime = ageInTicks;

        // Begin blending
        transitionStartTime = ageInTicks;
        transitionDuration = next.transitionTicks();
    }


    public void apply(
            HierarchicalModel<?> model,
            float ageInTicks,
            Vector3f animationCache
    ) {
        if (!initialized || current == null) {
            return;
        }

        /*
         * No previous animation means there's nothing
         * to crossfade from.
         */
        if (previous == null) {
            animate(
                    model,
                    current,
                    currentStartTime,
                    ageInTicks,
                    1.0F,
                    animationCache
            );

            return;
        }

        float progress;

        if (transitionDuration <= 0.0F) {
            progress = 1.0F;
        } else {
            progress =
                    (ageInTicks - transitionStartTime)
                            / transitionDuration;
        }

        progress = Math.clamp(progress, 0.0F, 1.0F);

        /*
         * Smoothstep instead of a perfectly linear transition.
         *
         * 0 -> slow
         * middle -> faster
         * 1 -> slow
         */
        float blend =
                progress * progress
                        * (3.0F - 2.0F * progress);


        // Previous animation fades out
        animate(
                model,
                previous,
                previousStartTime,
                ageInTicks,
                1.0F - blend,
                animationCache
        );

        // New animation fades in
        animate(
                model,
                current,
                currentStartTime,
                ageInTicks,
                blend,
                animationCache
        );


        /*
         * Transition finished.
         *
         * We no longer need to evaluate the old animation.
         */
        if (progress >= 1.0F) {
            previous = null;
        }
    }


    private void animate(
            HierarchicalModel<?> model,
            PonyAnimationLogic animation,
            float startTime,
            float ageInTicks,
            float weight,
            Vector3f animationCache
    ) {
        /*
         * Minecraft's keyframe system uses milliseconds here.
         *
         * 1 tick = 50 ms.
         */
        long elapsedMilliseconds =
                (long) ((ageInTicks - startTime) * 50.0F);

        KeyframeAnimations.animate(
                model,
                animation.definition(),
                elapsedMilliseconds,
                weight,
                animationCache
        );
    }


    public PonyAnimationLogic getCurrent() {
        return current;
    }

    public boolean isTransitioning() {
        return previous != null;
    }
}
