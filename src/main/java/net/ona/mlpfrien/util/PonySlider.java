package net.ona.mlpfrien.util;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.function.Consumer;

public class PonySlider extends AbstractSliderButton {
    private final Component label;
    private final float min, max;
    private final Consumer<Float> onChange;

    public PonySlider(int x, int y, int width, int height, Component label, float min, float max, float current, Consumer<Float> onChange) {
        super(x, y, width, height, CommonComponents.EMPTY, (current - min) / (max - min));
        this.label = label;
        this.min = min;
        this.max = max;
        this.onChange = onChange;
        updateMessage();
    }

    private float real() {
        return Mth.lerp((float) this.value, min, max);
    }

    @Override
    protected void updateMessage() {
        setMessage(Component.empty().append(label).append(String.format(": %.2fx", real())));
    }

    @Override
    protected void applyValue() {
        onChange.accept(real());
    }
}
