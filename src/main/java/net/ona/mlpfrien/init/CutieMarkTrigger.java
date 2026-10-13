package net.ona.mlpfrien.init;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class CutieMarkTrigger extends SimpleCriterionTrigger<CutieMarkTrigger.Instance> {

    @Override
    public Codec<CutieMarkTrigger.Instance> codec() {
        return Instance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    public record Instance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
        public static final Codec<Instance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(EntityPredicate.ADVANCEMENT_CODEC
                        .optionalFieldOf("player")
                        .forGetter(Instance::player)).apply(instance, Instance::new)
        );
    }
}
