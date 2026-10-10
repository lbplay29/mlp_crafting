package net.ona.mlpfrien.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.function.Consumer;

public class ColorPickerWidget extends AbstractWidget {

    private static final int GAP = 4;
    private static final int HUE_HEIGHT = 10;

    private final int pickerHeight;

    private float hue;
    private float saturation;
    private float brightness;

    private final Consumer<Integer> onChanged;

    private DragMode dragMode = DragMode.NONE;

    private enum DragMode {
        NONE,
        COLOR,
        HUE
    }

    public ColorPickerWidget(
            int x,
            int y,
            int width,
            int pickerHeight,
            int initialColor,
            Consumer<Integer> onChanged
    ) {
        super(
                x,
                y,
                width,
                pickerHeight + GAP + HUE_HEIGHT,
                Component.literal("Color Picker")
        );

        this.pickerHeight = pickerHeight;
        this.onChanged = onChanged;

        setColor(initialColor, false);
    }


    // =========================================================
    // Rendering
    // =========================================================

    @Override
    protected void renderWidget(
            GuiGraphics graphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        renderColorArea(graphics);
        renderHueBar(graphics);

        renderColorCursor(graphics);
        renderHueCursor(graphics);
    }


    private void renderColorArea(GuiGraphics graphics) {

        /*
         * Each X coordinate represents saturation.
         *
         * At the top:
         * brightness = 1
         *
         * At the bottom:
         * brightness = 0
         *
         * So each column is just a vertical gradient
         * from that saturation's color -> black.
         */
        for (int i = 0; i < width; i++) {

            float saturation =
                    width <= 1
                            ? 0.0F
                            : (float) i / (width - 1);

            int topColor =
                    0xFF000000 |
                            Mth.hsvToRgb(
                                    hue,
                                    saturation,
                                    1.0F
                            );

            int bottomColor = 0xFF000000;

            graphics.fillGradient(
                    getX() + i,
                    getY(),
                    getX() + i + 1,
                    getY() + pickerHeight,
                    topColor,
                    bottomColor
            );
        }
    }


    private void renderHueBar(GuiGraphics graphics) {

        int hueY = getHueY();

        for (int i = 0; i < width; i++) {

            float h =
                    width <= 1
                            ? 0.0F
                            : (float) i / (width - 1);

            int color =
                    0xFF000000 |
                            Mth.hsvToRgb(
                                    h,
                                    1.0F,
                                    1.0F
                            );

            graphics.fill(
                    getX() + i,
                    hueY,
                    getX() + i + 1,
                    hueY + HUE_HEIGHT,
                    color
            );
        }
    }


    // =========================================================
    // Selection indicators
    // =========================================================

    private void renderColorCursor(GuiGraphics graphics) {

        int cursorX =
                getX() +
                        Math.round(saturation * (width - 1));

        int cursorY =
                getY() +
                        Math.round(
                                (1.0F - brightness)
                                        * (pickerHeight - 1)
                        );

        // Black outer cross
        graphics.fill(
                cursorX - 3,
                cursorY,
                cursorX + 4,
                cursorY + 1,
                0xFF000000
        );

        graphics.fill(
                cursorX,
                cursorY - 3,
                cursorX + 1,
                cursorY + 4,
                0xFF000000
        );

        // White inner cross
        graphics.fill(
                cursorX - 2,
                cursorY,
                cursorX + 3,
                cursorY + 1,
                0xFFFFFFFF
        );

        graphics.fill(
                cursorX,
                cursorY - 2,
                cursorX + 1,
                cursorY + 3,
                0xFFFFFFFF
        );
    }


    private void renderHueCursor(GuiGraphics graphics) {

        int cursorX =
                getX() +
                        Math.round(hue * (width - 1));

        int hueY = getHueY();

        graphics.fill(
                cursorX - 1,
                hueY - 1,
                cursorX + 2,
                hueY + HUE_HEIGHT + 1,
                0xFF000000
        );

        graphics.fill(
                cursorX,
                hueY,
                cursorX + 1,
                hueY + HUE_HEIGHT,
                0xFFFFFFFF
        );
    }


    // =========================================================
    // Mouse handling
    // =========================================================

    @Override
    public boolean mouseClicked(
            double mouseX,
            double mouseY,
            int button
    ) {
        if (button != 0 || !active || !visible) {
            return false;
        }

        if (isInsideColorArea(mouseX, mouseY)) {

            dragMode = DragMode.COLOR;

            updateColorPosition(
                    mouseX,
                    mouseY
            );

            return true;
        }

        if (isInsideHueBar(mouseX, mouseY)) {

            dragMode = DragMode.HUE;

            updateHuePosition(mouseX);

            return true;
        }

        return false;
    }


    @Override
    public boolean mouseDragged(
            double mouseX,
            double mouseY,
            int button,
            double dragX,
            double dragY
    ) {
        if (button != 0) {
            return false;
        }

        switch (dragMode) {

            case COLOR -> {
                updateColorPosition(
                        mouseX,
                        mouseY
                );

                return true;
            }

            case HUE -> {
                updateHuePosition(mouseX);

                return true;
            }

            default -> {
                return false;
            }
        }
    }


    @Override
    public boolean mouseReleased(
            double mouseX,
            double mouseY,
            int button
    ) {
        dragMode = DragMode.NONE;

        return super.mouseReleased(
                mouseX,
                mouseY,
                button
        );
    }


    // =========================================================
    // Mouse -> HSV
    // =========================================================

    private void updateColorPosition(
            double mouseX,
            double mouseY
    ) {

        saturation =
                (float) (
                        (mouseX - getX())
                                / (double) width
                );

        brightness =
                1.0F -
                        (float) (
                                (mouseY - getY())
                                        / (double) pickerHeight
                        );

        saturation =
                Mth.clamp(
                        saturation,
                        0.0F,
                        1.0F
                );

        brightness =
                Mth.clamp(
                        brightness,
                        0.0F,
                        1.0F
                );

        colorChanged();
    }


    private void updateHuePosition(
            double mouseX
    ) {

        hue =
                (float) (
                        (mouseX - getX())
                                / (double) width
                );

        hue =
                Mth.clamp(
                        hue,
                        0.0F,
                        1.0F
                );

        colorChanged();
    }


    // =========================================================
    // Color access
    // =========================================================

    public int getColor() {
        return Mth.hsvToRgb(
                hue,
                saturation,
                brightness
        ) & 0xFFFFFF;
    }


    public void setColor(int rgb) {
        setColor(rgb, true);
    }


    public void setColor(
            int rgb,
            boolean notify
    ) {
        float[] hsv = rgbToHsv(rgb);

        hue = hsv[0];
        saturation = hsv[1];
        brightness = hsv[2];

        if (notify) {
            colorChanged();
        }
    }


    private void colorChanged() {

        if (onChanged != null) {
            onChanged.accept(
                    getColor()
            );
        }
    }


    // =========================================================
    // RGB -> HSV
    // =========================================================

    private static float[] rgbToHsv(int color) {

        float red =
                ((color >> 16) & 0xFF)
                        / 255.0F;

        float green =
                ((color >> 8) & 0xFF)
                        / 255.0F;

        float blue =
                (color & 0xFF)
                        / 255.0F;


        float max =
                Math.max(
                        red,
                        Math.max(green, blue)
                );

        float min =
                Math.min(
                        red,
                        Math.min(green, blue)
                );

        float delta = max - min;


        float hue;

        if (delta == 0.0F) {

            hue = 0.0F;

        } else if (max == red) {

            hue =
                    ((green - blue) / delta)
                            % 6.0F;

        } else if (max == green) {

            hue =
                    ((blue - red) / delta)
                            + 2.0F;

        } else {

            hue =
                    ((red - green) / delta)
                            + 4.0F;
        }


        hue /= 6.0F;

        if (hue < 0.0F) {
            hue += 1.0F;
        }


        float saturation =
                max == 0.0F
                        ? 0.0F
                        : delta / max;


        float brightness = max;


        return new float[]{
                hue,
                saturation,
                brightness
        };
    }


    // =========================================================
    // Hit boxes
    // =========================================================

    private int getHueY() {
        return getY()
                + pickerHeight
                + GAP;
    }


    private boolean isInsideColorArea(
            double mouseX,
            double mouseY
    ) {
        return mouseX >= getX()
                && mouseX < getX() + width
                && mouseY >= getY()
                && mouseY < getY() + pickerHeight;
    }


    private boolean isInsideHueBar(
            double mouseX,
            double mouseY
    ) {
        int hueY = getHueY();

        return mouseX >= getX()
                && mouseX < getX() + width
                && mouseY >= hueY
                && mouseY < hueY + HUE_HEIGHT;
    }


    // Required by AbstractWidget
    @Override
    protected void updateWidgetNarration(
            NarrationElementOutput output
    ) {
        defaultButtonNarrationText(output);
    }
}
