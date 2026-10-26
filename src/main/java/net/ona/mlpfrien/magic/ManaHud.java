package net.ona.mlpfrien.magic;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.data.UnicornData;

public final class ManaHud {

    private static final ResourceLocation MANA_BAR = ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, "textures/gui/mana_bar.png");
    private static final ResourceLocation MANA_BAR_EMPTY = ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, "textures/gui/manabar_empty.png");

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        if (!player.getData(PonyAttachments.PONY_DATA).race().canUseMagic()) {
            return;
        }
        UnicornData data = player.getData(PonyAttachments.UNICORN_DATA);

        int barWidth = 138;
        int barHeight = 25;
        int x = 0;
        int y = guiGraphics.guiHeight() - barHeight;

        guiGraphics.blit(MANA_BAR_EMPTY, x, y, 0, 0, barWidth, barHeight, barWidth, barHeight);

        int frameWidth = 138;
        int frameHeight = 25;

        int frameCount = 5;
        int ticksPerFrame = 5;

        int frame = (player.tickCount / ticksPerFrame) % frameCount;
        int v = frame * frameHeight;
        float percentage = data.max_mana() <= 0 ? 0 : data.mana() / data.max_mana();
        int filledWidth = (int)(frameWidth * percentage);

        guiGraphics.blit(MANA_BAR, x, y, 0, barHeight+v, filledWidth, frameHeight, frameWidth, frameHeight * frameCount);
    }
}
