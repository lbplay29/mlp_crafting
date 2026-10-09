package net.ona.mlpfrien.ponyvisuals.animation;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.player.Player;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.data.PonyData;
import net.ona.mlpfrien.ponyvisuals.models.PonyAnimation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PonyAnimationManager {

    private static final Map<UUID, PonyAnimationController> CONTROLLERS = new HashMap<>();

    public static AnimationState headAttack = new AnimationState();
    public static AnimationState itemAttack = new AnimationState();
    public static AnimationState uniAttack = new AnimationState();
    private static float lastAttackAnim = 0.0F;

    public static AnimationState attackType(Player entity) {
        PonyData data = entity.getData(PonyAttachments.PONY_DATA);
        float attackAnim = entity.getAttackAnim(entity.tickCount);

        if (attackAnim > 0.0F && lastAttackAnim <= 0.0F) {
            if (!entity.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                if (data.race() == PonyData.RACES.UNICORN) {
                    uniAttack.start(entity.tickCount);
                } else {
                    itemAttack.start(entity.tickCount);
                }
            } else {
                headAttack.start(entity.tickCount);
            }
        }
        lastAttackAnim = attackAnim;

        if (uniAttack.isStarted()) {
            return uniAttack;
        } else if (itemAttack.isStarted()) {
            return itemAttack;
        }
        return headAttack;
    }

    public static void stopAnimation(AnimationState state, AnimationDefinition animation) {
        if (state.isStarted()) {
            long durationMs = (long) (animation.lengthInSeconds() * 1000.0F);

            if (state.getAccumulatedTime() >= durationMs) {
                state.stop();
            }
        }
    }

    public static AnimationDefinition attackGet(Player entity) {
        if (attackType(entity)==itemAttack) {
            return PonyAnimation.attack;
        } else if (attackType(entity)==uniAttack) {
            return PonyAnimation.attack_unicorn;
        }
        return PonyAnimation.attack_head;
    }

    public static PonyAnimationController get(AbstractClientPlayer player) {
        return CONTROLLERS.computeIfAbsent(player.getUUID(), uuid -> new PonyAnimationController());
    }

    public static void remove(UUID uuid) {
        CONTROLLERS.remove(uuid);
    }


    public static void clear() {
        CONTROLLERS.clear();
    }
}
