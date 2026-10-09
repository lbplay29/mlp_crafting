package net.ona.mlpfrien.ponyvisuals.gui;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class IntSlider extends AbstractSliderButton {
    private final int min;
    private final int max;
    private final Consumer<Integer> onChanged;
    private final String message;

    public IntSlider(int x, int y, int width, int height, int initialValue, int min, int max, String message, Consumer<Integer> onChanged) {
        super(x, y, width, height, Component.empty(), (double) (initialValue - min)/(max-min));

        this.min = min;
        this.max = max;
        this.onChanged = onChanged;
        this.message=message;
    }

    public int getValue() {
        return min + (int) Math.round(value * (max - min));
    }

    @Override
    protected void updateMessage() {
        setMessage(Component.literal(message+getValue()));
    }

    @Override
    protected void applyValue() {
        onChanged.accept(getValue());
    }
}
