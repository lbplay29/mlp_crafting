package net.ona.mlpfrien.data.skill;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.ona.mlpfrien.data.LevelData;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.gui.SkillTreeGUI;

public final class SkillManager {
    private SkillManager() {}

    public static boolean isNodeUnlocked(SkillNodes.SkillNode node, LevelData data) {
        return data.skills().contains(node.skill());
    }
    public static boolean isNodeUnlocked(ResourceLocation nodeId, LevelData data) {
        SkillNodes.SkillNode node = SkillNodes.get(nodeId);
        return node != null && isNodeUnlocked(node, data);
    }

    public static boolean hasSkill(Player player, ResourceLocation skill) {
        return player.getData(PonyAttachments.LEVEL_DATA).skills().contains(skill);
    }


    public static boolean areParentsMet(SkillNodes.SkillNode node, LevelData data) {
        if (node.parents().isEmpty()) {
            return true;
        }
        return node.parents().stream().anyMatch(parent -> isNodeUnlocked(parent, data));
    }


    public static boolean isBlocked(SkillNodes.SkillNode node, LevelData data) {
        return node.locks().stream().anyMatch(lock -> isNodeUnlocked(lock, data));
    }


    public static SkillTreeGUI.SkillNodeState getState(SkillNodes.SkillNode node, LevelData data) {
        if (isNodeUnlocked(node, data)) {
            return SkillTreeGUI.SkillNodeState.UNLOCKED;
        }
        if (isBlocked(node, data)) {
            return SkillTreeGUI.SkillNodeState.BLOCKED;
        }
        if (!areParentsMet(node, data)) {
            return SkillTreeGUI.SkillNodeState.LOCKED;
        }
        if (data.skillPoints() < node.cost()) {
            return SkillTreeGUI.SkillNodeState.LOCKED;
        }
        return SkillTreeGUI.SkillNodeState.AVAILABLE;
    }


    public static boolean canUnlock(SkillNodes.SkillNode node, LevelData data) {
        return getState(node, data) == SkillTreeGUI.SkillNodeState.AVAILABLE;
    }
}
