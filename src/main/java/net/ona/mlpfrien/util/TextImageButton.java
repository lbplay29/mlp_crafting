package net.ona.mlpfrien.util;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;

public class TextImageButton extends ImageButton {
    private final Font font;

    public TextImageButton(int x, int y, int width, int height, WidgetSprites sprites, OnPress onPress, Component message, Font font) {
        super(x, y, width, height, sprites, onPress, message);

        this.font = font;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);

        int textX = getX() + (getWidth() - font.width(getMessage())) / 2;
        int textY = getY() + (getHeight() - font.lineHeight) / 2;

        guiGraphics.drawString(font, getMessage(), textX+1, textY, 0x202020, false);
    }
}
