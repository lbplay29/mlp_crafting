package net.ona.mlpfrien.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.LevelData;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.data.PonyData;
import net.ona.mlpfrien.data.skill.PonySkills;
import net.ona.mlpfrien.data.skill.SkillManager;
import net.ona.mlpfrien.data.skill.SkillNodes;
import net.ona.mlpfrien.data.skill.SkillTrees;
import net.ona.mlpfrien.network.UnlockSkillPayload;

import java.util.Collection;
import java.util.List;

import static net.ona.mlpfrien.util.BasicUtilities.id;

public class SkillTreeGUI extends Screen {
    protected SkillTreeGUI() {
        super(Component.literal("yo mama"));
    }
    private Player curPlayer;
    private Collection<PonySkills.PonySkill> skills;
    private final WidgetSprites TEXT_OPTIONS = new WidgetSprites(id("textbox_letter_gui"),id("textbox_letter_gui03"),id("textbox_letter_gui02"),
            id("textbox_letter_gui03"));
    public enum SkillNodeState {
        UNLOCKED,
        AVAILABLE,
        LOCKED,
        BLOCKED,
        HIDDEN
    }
    private SkillTrees.SkillTree tree;
    private List<SkillTrees.SkillTree> availableTrees = List.of();
    private int treeOriginX;
    private int treeOriginY;
    private final int NODE_SIZE = 18;

    @Override
    protected void init() {
        super.init();
        curPlayer = minecraft.player;

        PonyData ponyData = curPlayer.getData(PonyAttachments.PONY_DATA);

        availableTrees = SkillTrees.getAll().stream().filter(skillTree -> skillTree.isAvailable(ponyData)).toList();
        if (!availableTrees.isEmpty()) {
            tree = availableTrees.getFirst();
        }
        treeOriginX = width / 2;
        treeOriginY = 50;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        curPlayer = Minecraft.getInstance().player;
        if (tree==null) {return;}

        LevelData data = curPlayer.getData(PonyAttachments.LEVEL_DATA);
        for (ResourceLocation nodeId : tree.nodes()) {
            SkillNodes.SkillNode node = SkillNodes.get(nodeId);
            for (ResourceLocation parentId : node.parents()) {
                SkillNodes.SkillNode parent = SkillNodes.get(parentId);
                drawConnection(guiGraphics, parent, node, data);
            }
        }
        for (ResourceLocation nodeId : tree.nodes()) {
            SkillNodes.SkillNode node = SkillNodes.get(nodeId);
            drawSkillNode(guiGraphics,node, data,mouseX,mouseY);
        }
        guiGraphics.drawString(font, Component.literal("Points: "+data.skillPoints()), treeOriginX/2+200, treeOriginY/2,0xFFFFFF);
    }

    private void drawSkillNode(GuiGraphics graphics, SkillNodes.SkillNode node, LevelData data, int mouseX, int mouseY) {
        PonySkills.PonySkill skill = PonySkills.get(node.skill());
        if (skill == null) {
            return;
        }
        int centerX = treeOriginX + node.x();
        int centerY = treeOriginY + node.y();
        int x = centerX - NODE_SIZE / 2;
        int y = centerY - NODE_SIZE / 2;

        SkillNodeState state = SkillManager.getState(node, data);

        int borderColor = switch (state) {
            case UNLOCKED -> 0xFFFFFFFF;
            case AVAILABLE -> 0xFF55FF55;
            case LOCKED -> 0xFF777777;
            case BLOCKED -> 0xFFFF5555;
            case HIDDEN -> 0x00000000;
        };

        graphics.fill(x, y, x + NODE_SIZE, y + NODE_SIZE, 0xCC111111);

        graphics.renderOutline(x, y, NODE_SIZE, NODE_SIZE, borderColor);
        /*
         * If skill.icon() refers to a GUI sprite:
         * assets/mlpfrien/textures/gui/sprites/skills/air_dash.png
         * then the RL would be:
         * mlpfrien:skills/air_dash
         */
        graphics.blitSprite(skill.icon(), x+1, y+1, 16, 16);

        if (state == SkillNodeState.LOCKED) {
            graphics.fill(x + 1, y + 1, x + NODE_SIZE - 1, y + NODE_SIZE - 1, 0x88000000);
        }
        if (state == SkillNodeState.BLOCKED) {
            graphics.fill(x + 1, y + 1, x + NODE_SIZE - 1, y + NODE_SIZE - 1, 0x88AA0000);
        }

        boolean hovered = mouseX >= x && mouseX < x + NODE_SIZE && mouseY >= y && mouseY < y + NODE_SIZE;
        if (hovered) {
            List<Component> tooltip = List.of(skill.name(), skill.description(), Component.literal("Cost: " + node.cost()));
            graphics.renderComponentTooltip(font, tooltip, mouseX, mouseY);
        }
    }

    private void drawConnection(GuiGraphics graphics, SkillNodes.SkillNode parent, SkillNodes.SkillNode child, LevelData data) {
        int x1 = treeOriginX + parent.x();
        int y1 = treeOriginY + parent.y();
        int x2 = treeOriginX + child.x();
        int y2 = treeOriginY + child.y();
        int middleY = (y1 + y2) / 2;
        int color;

        if (SkillManager.isNodeUnlocked(child, data)) {
            color = 0xFFFFFFFF;
        } else if (SkillManager.isNodeUnlocked(parent, data)) {
            color = 0xFFAAAAAA;
        } else {
            color = 0xFF555555;
        }

        graphics.vLine(x1, Math.min(y1, middleY), Math.max(y1, middleY), color);
        graphics.hLine(Math.min(x1, x2), Math.max(x1, x2), middleY, color);
        graphics.vLine(x2, Math.min(middleY, y2), Math.max(middleY, y2), color);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (tree == null || minecraft.player == null) {
            return super.mouseClicked(mouseX, mouseY, button);
        }
        LevelData data = minecraft.player.getData(PonyAttachments.LEVEL_DATA);
        for (ResourceLocation nodeId : tree.nodes()) {
            SkillNodes.SkillNode node = SkillNodes.get(nodeId);
            int centerX = treeOriginX + node.x();
            int centerY = treeOriginY + node.y();
            int x = centerX - NODE_SIZE / 2;
            int y = centerY - NODE_SIZE / 2;
            if (mouseX >= x && mouseX < x + NODE_SIZE && mouseY >= y && mouseY < y + NODE_SIZE) {
                if (SkillManager.canUnlock(node, data)) {
                    PacketDistributor.sendToServer(new UnlockSkillPayload(node.id()));
                }
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
