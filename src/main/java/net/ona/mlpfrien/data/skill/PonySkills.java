package net.ona.mlpfrien.data.skill;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

import static net.ona.mlpfrien.util.BasicUtilities.id;

public final class PonySkills {
    private static final Map<ResourceLocation, PonySkill> SKILLS = new LinkedHashMap<>();
    public record PonySkill(ResourceLocation id, Component name, Component description, ResourceLocation icon) {}

    public static final PonySkill ROOT_CM = register(new PonySkill(id("root_cm"),
            Component.translatable("skill.mlpfrien.root_cm"),
            Component.translatable("skill.mlpfrien.root_cm.description"),
            id("skills/root_cm")));

    public static final PonySkill HIGH_RANGE = register(new PonySkill(id("high_range"),
            Component.translatable("skill.mlpfrien.high_range"),
            Component.translatable("skill.mlpfrien.high_range.description"),
            id("skills/high_range")));
    public static final PonySkill REPLACING = register(new PonySkill(id("replacing"),
            Component.translatable("skill.mlpfrien.replacing"),
            Component.translatable("skill.mlpfrien.replacing.description"),
            id("skills/replacing")));
    public static final PonySkill EXTRA_BREAKING = register(new PonySkill(id("extra_breaking"),
            Component.translatable("skill.mlpfrien.extra_breaking"),
            Component.translatable("skill.mlpfrien.extra_breaking.description"),
            id("skills/extra_breaking")));

    public static final PonySkill FALL_RESIST = register(new PonySkill(id("fall_resist"),
            Component.translatable("skill.mlpfrien.fall_resist"),
            Component.translatable("skill.mlpfrien.fall_resist.description"),
            id("skills/fall_resist")));
    public static final PonySkill HIGH_SPEED = register(new PonySkill(id("high_speed"),
            Component.translatable("skill.mlpfrien.high_speed"),
            Component.translatable("skill.mlpfrien.high_speed.description"),
            id("skills/high_speed")));
    public static final PonySkill HIGH_JUMP = register(new PonySkill(id("high_jump"),
            Component.translatable("skill.mlpfrien.high_jump"),
            Component.translatable("skill.mlpfrien.high_jump.description"),
            id("skills/high_jump")));

    public static final PonySkill FAST_MINING = register(new PonySkill(id("fast_mining"),
            Component.translatable("skill.mlpfrien.fast_mining"),
            Component.translatable("skill.mlpfrien.fast_mining.description"),
            id("skills/fast_mining")));
    public static final PonySkill EXTRA_FORTUNE = register(new PonySkill(id("extra_fortune"),
            Component.translatable("skill.mlpfrien.extra_fortune"),
            Component.translatable("skill.mlpfrien.extra_fortune.description"),
            id("skills/extra_fortune")));
    public static final PonySkill HIGH_VISION = register(new PonySkill(id("high_vision"),
            Component.translatable("skill.mlpfrien.high_vision"),
            Component.translatable("skill.mlpfrien.high_vision.description"),
            id("skills/high_vision")));


    public static final PonySkill ROOT_PEGASUS = register(new PonySkill(id("root_pegasus"),
            Component.translatable("skill.mlpfrien.air_dash"),
            Component.translatable("skill.mlpfrien.air_dash.description"),
            id("skills/root_pegasus")));
    public static final PonySkill AIR_DASH = register(new PonySkill(id("air_dash"),
            Component.translatable("skill.mlpfrien.air_dash"),
            Component.translatable("skill.mlpfrien.air_dash.description"),
            id("skills/air_dash")));
    public static final PonySkill STRONG_WINGS = register(new PonySkill(id("air_dash"),
            Component.translatable("skill.mlpfrien.air_dash"),
            Component.translatable("skill.mlpfrien.air_dash.description"),
            id("skills/air_dash")));

    private static PonySkill register(PonySkill skill) {
        SKILLS.put(skill.id(), skill);
        return skill;
    }

    public static PonySkill get(ResourceLocation id) {
        return SKILLS.get(id);
    }

    public static Collection<PonySkill> getAll() {
        return SKILLS.values();
    }
}
