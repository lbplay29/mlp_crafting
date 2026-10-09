package net.ona.mlpfrien.ponyvisuals.gui;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.magic.SpellComponent;

import java.util.ArrayList;
import java.util.List;

public class SpellManagerGUI extends Screen {
    public SpellManagerGUI() {
        super(Component.literal("penor"));
    }

    private final List<SpellComponent> editingComponents = new ArrayList<>();
    private final List<AbstractWidget> spellComponentButtons = new ArrayList<>();

    private WidgetSprites getComponent(SpellComponent component) {
        return switch (component) {
            case DAMAGE -> new WidgetSprites(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"textbox_confirm_gui"),
                    ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"textbox_confirm_gui02"));
            case TELEPORT -> new WidgetSprites(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"textbox_confirm_gui"),
                    ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"textbox_confirm_gui02"));
            case AMPLIFY -> new WidgetSprites(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"textbox_confirm_gui"),
                    ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"textbox_confirm_gui02"));
            case WAIT -> new WidgetSprites(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"textbox_confirm_gui"),
                    ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"textbox_confirm_gui02"));
            case FIRE -> new WidgetSprites(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"textbox_confirm_gui"),
                    ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"textbox_confirm_gui02"));
            case SELF -> new WidgetSprites(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"textbox_confirm_gui"),
                    ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"textbox_confirm_gui02"));
            case PROJECTILE -> new WidgetSprites(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"textbox_confirm_gui"),
                    ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"textbox_confirm_gui02"));
            case TOUCH -> new WidgetSprites(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"textbox_confirm_gui"),
                    ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"textbox_confirm_gui02"));
            case EXPLODE -> new WidgetSprites(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"textbox_confirm_gui"),
                    ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"textbox_confirm_gui02"));
            case HEAL -> new WidgetSprites(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"textbox_confirm_gui"),
                    ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"textbox_confirm_gui02"));
        };
    }

    @Override
    protected void init() {
        super.init();
        buildAvailableComponents();
    }

    private void buildAvailableComponents() {
        SpellComponent[] components = SpellComponent.values();

        int startX = 30;
        int startY = 50;

        int columns = 6;

        for (int i = 0; i < components.length; i++) {
            SpellComponent component = components[i];

            int column = i % columns;
            int row = i / columns;

            int x = startX + column * 22;
            int y = startY + row * 22;

            ImageButton button = new ImageButton(x, y, 20, 20, getComponent(component), clicked -> {
                                editingComponents.add(component);
                                rebuildSpellComponentButtons();
                            }, Component.literal(component.name()));
            addRenderableWidget(button);
        }
    }

    private void rebuildSpellComponentButtons() {
        // Remove old component widgets
        for (AbstractWidget widget : spellComponentButtons) {
            removeWidget(widget);
        }
        spellComponentButtons.clear();

        int startX = 40;
        int y = 150;

        for (int i = 0; i < editingComponents.size(); i++) {
            int index = i;

            SpellComponent component = editingComponents.get(i);

            ImageButton button = new ImageButton(startX + i * 22, y, 20, 20, getComponent(component), clicked -> {
                                editingComponents.remove(index);
                                rebuildSpellComponentButtons();
                            }, Component.literal(component.name()));
            addRenderableWidget(button);
            spellComponentButtons.add(button);
        }
    }
}