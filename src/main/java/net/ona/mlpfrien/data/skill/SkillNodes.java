package net.ona.mlpfrien.data.skill;

import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static net.ona.mlpfrien.util.BasicUtilities.id;

public class SkillNodes {
    private static final Map<ResourceLocation, SkillNode> SKILL_NODES = new LinkedHashMap<>();
    public record SkillNode(ResourceLocation id, ResourceLocation skill, List<ResourceLocation> parents, List<ResourceLocation> locks, int x, int y, int cost) {}
    private static ResourceLocation replacing() {return id("cutie_mark/building/replacing");}
    private static ResourceLocation high_speed() {return id("cutie_mark/exploring/high_speed");}
    private static ResourceLocation extra_fortune() {return id("cutie_mark/mining/extra_fortune");}

    public static final SkillNode ROOT_CM_M = registerNode(new SkillNode(id("cutie_mark/mining/root"),
            PonySkills.ROOT_CM.id(), List.of(), List.of(), 0, 0, 0));
    public static final SkillNode ROOT_CM_B = registerNode(new SkillNode(id("cutie_mark/building/root"),
            PonySkills.ROOT_CM.id(), List.of(), List.of(), 0, 0, 0));
    public static final SkillNode ROOT_CM_E = registerNode(new SkillNode(id("cutie_mark/exploring/root"),
            PonySkills.ROOT_CM.id(), List.of(), List.of(), 0, 0, 0));

    public static final SkillNode HIGH_RANGE = registerNode(new SkillNode(id("cutie_mark/building/high_range"),
            PonySkills.HIGH_RANGE.id(), List.of(ROOT_CM_B.id()), List.of(), 0, 50, 2));
    public static final SkillNode EXTRA_BREAKING = registerNode(new SkillNode(id("cutie_mark/building/extra_breaking"),
            PonySkills.EXTRA_BREAKING.id(), List.of(HIGH_RANGE.id()), List.of(replacing()), 50, 100, 4));
    public static final SkillNode REPLACING = registerNode(new SkillNode(replacing(),
            PonySkills.REPLACING.id(), List.of(HIGH_RANGE.id()), List.of(EXTRA_BREAKING.id()), -50, 100, 4));

    public static final SkillNode FALL_RESIST = registerNode(new SkillNode(id("cutie_mark/exploring/fall_resist"),
            PonySkills.FALL_RESIST.id(), List.of(ROOT_CM_E.id()), List.of(), 0, 50, 1));
    public static final SkillNode HIGH_JUMP = registerNode(new SkillNode(id("cutie_mark/exploring/high_jump"),
            PonySkills.HIGH_JUMP.id(), List.of(FALL_RESIST.id()), List.of(high_speed()), 50, 100, 4));
    public static final SkillNode HIGH_SPEED = registerNode(new SkillNode(high_speed(),
            PonySkills.HIGH_SPEED.id(), List.of(FALL_RESIST.id()), List.of(HIGH_JUMP.id()), -50, 100, 4));

    public static final SkillNode FAST_MINING = registerNode(new SkillNode(id("cutie_mark/mining/fast_mining"),
            PonySkills.FAST_MINING.id(), List.of(ROOT_CM_M.id()), List.of(), 0, 50, 1));
    public static final SkillNode HIGH_VISION = registerNode(new SkillNode(id("cutie_mark/mining/high_vision"),
            PonySkills.HIGH_JUMP.id(), List.of(FAST_MINING.id()), List.of(extra_fortune()), 50, 100, 4));
    public static final SkillNode EXTRA_FORTUNE = registerNode(new SkillNode(extra_fortune(),
            PonySkills.EXTRA_FORTUNE.id(), List.of(FAST_MINING.id()), List.of(HIGH_VISION.id()), -50, 100, 4));


    public static final SkillNode ROOT_PEGASUS = registerNode(new SkillNode(id("pegasus/root"),
            PonySkills.ROOT_PEGASUS.id(), List.of(), List.of(), 0, 0, 0));
    public static final SkillNode STRONG_WINGS_NODE = registerNode(new SkillNode(id("pegasus/strong_wings"),
            PonySkills.STRONG_WINGS.id(), List.of(ROOT_PEGASUS.id()), List.of(), 0, 50, 1));
    public static final SkillNode AIR_DASH_NODE = registerNode(new SkillNode(id("pegasus/air_dash"),
            PonySkills.AIR_DASH.id(), List.of(STRONG_WINGS_NODE.id()), List.of(), -50, 100, 1));

    private static SkillNode registerNode(SkillNode skill) {
        SKILL_NODES.put(skill.id(), skill);
        return skill;
    }

    public static SkillNode get(ResourceLocation id) {
        return SKILL_NODES.get(id);
    }

    public static Collection<SkillNode> getAll() {
        return SKILL_NODES.values();
    }
}
