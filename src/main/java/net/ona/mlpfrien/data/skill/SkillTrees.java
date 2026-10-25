package net.ona.mlpfrien.data.skill;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.ona.mlpfrien.data.PonyData;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static net.ona.mlpfrien.util.BasicUtilities.id;

public class SkillTrees {
    private static final Map<ResourceLocation, SkillTree> SKILL_TREES = new LinkedHashMap<>();

    public static SkillTree getTreeForNode(ResourceLocation id) {
        for (SkillTree tree : SKILL_TREES.values()) {
            if (tree.nodes.contains(id)) {
                return tree;
            }
        }
        return null;
    }

    public record SkillTree(ResourceLocation id, Component name, ResourceLocation icon, Predicate<PonyData> requirement, List<ResourceLocation> nodes) {
        public boolean isAvailable(PonyData data) {return requirement.test(data);}
    }

    public static final SkillTree CM_BUILDING = registerTree(new SkillTree(id("cm_building"),
            Component.translatable("skill_tree.mlpfrien.cm_building"), id("textures/gui/skill_tree/cm_building.png"),
            pony -> pony.cutieMark().equals(PonyData.CUTIE_MARKS.BUILDING) && pony.discoveredCM(),
            List.of(SkillNodes.ROOT_CM_B.id(), SkillNodes.HIGH_RANGE.id(), SkillNodes.EXTRA_BREAKING.id(),SkillNodes.REPLACING.id())));
    public static final SkillTree CM_EXPLORING = registerTree(new SkillTree(id("cm_exploring"),
            Component.translatable("skill_tree.mlpfrien.cm_exploring"), id("textures/gui/skill_tree/cm_exploring.png"),
            pony -> pony.cutieMark().equals(PonyData.CUTIE_MARKS.EXPLORING) && pony.discoveredCM(),
            List.of(SkillNodes.ROOT_CM_E.id(), SkillNodes.HIGH_JUMP.id(),SkillNodes.HIGH_SPEED.id(),SkillNodes.FALL_RESIST.id())));
    public static final SkillTree CM_MINING = registerTree(new SkillTree(id("cm_mining"),
            Component.translatable("skill_tree.mlpfrien.cm_mining"), id("textures/gui/skill_tree/cm_mining.png"),
            pony -> pony.cutieMark().equals(PonyData.CUTIE_MARKS.MINING) && pony.discoveredCM(),
            List.of(SkillNodes.ROOT_CM_M.id(), SkillNodes.HIGH_VISION.id(),SkillNodes.FAST_MINING.id(),SkillNodes.EXTRA_FORTUNE.id())));

    public static final SkillTree PEGASUS = registerTree(new SkillTree(id("pegasus"),
            Component.translatable("skill_tree.mlpfrien.pegasus"), id("textures/gui/skill_tree/pegasus.png"),
            pony -> false,
            List.of(SkillNodes.AIR_DASH_NODE.id(), SkillNodes.STRONG_WINGS_NODE.id(), SkillNodes.ROOT_PEGASUS.id())));

    private static SkillTree registerTree(SkillTree skill) {
        SKILL_TREES.put(skill.id(), skill);
        return skill;
    }
    public static SkillTree get(ResourceLocation id) {return SKILL_TREES.get(id);}
    public static Collection<SkillTree> getAll() {return SKILL_TREES.values();}
}
