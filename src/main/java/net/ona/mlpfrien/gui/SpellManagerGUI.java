package net.ona.mlpfrien.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.gui.spell.SpellList;
import net.ona.mlpfrien.magic.SpellComponent;
import net.ona.mlpfrien.magic.UnicornData;
import net.ona.mlpfrien.magic.UnicornSpell;
import net.ona.mlpfrien.network.CreateSpellPayload;
import net.ona.mlpfrien.network.EquipSpellPayload;
import net.ona.mlpfrien.network.RemoveSpellPayload;
import net.ona.mlpfrien.network.ReplaceSpellPayload;

import java.util.ArrayList;
import java.util.List;

public class SpellManagerGUI extends Screen {
    public SpellManagerGUI() {
        super(Component.literal("penor"));
    }

    private final List<SpellComponent> editingComponents = new ArrayList<>();
    private final List<AbstractWidget> spellComponentButtons = new ArrayList<>();
    private String editingName = "";
    private int selectedSlot = 0;
    private EditBox spellNameBox;
    private SpellList spellList;
    private UnicornData lastData;


    private WidgetSprites getComponent(SpellComponent component) {
        return switch (component) {
            case DAMAGE -> new WidgetSprites(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"damage"),
                    ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"damage"));
            case TELEPORT -> new WidgetSprites(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"teleport"),
                    ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"teleport"));
            case AMPLIFY -> new WidgetSprites(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"amplify"),
                    ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"amplify"));
            case WAIT -> new WidgetSprites(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"wait"),
                    ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"wait"));
            case FIRE -> new WidgetSprites(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"fire"),
                    ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"fire"));
            case SELF -> new WidgetSprites(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"self"),
                    ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"self"));
            case PROJECTILE -> new WidgetSprites(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"projectile"),
                    ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"projectile"));
            case TOUCH -> new WidgetSprites(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"touch"),
                    ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"touch"));
            case EXPLODE -> new WidgetSprites(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"explosive"),
                    ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"explosive"));
            case HEAL -> new WidgetSprites(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"heal"),
                    ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"heal"));
        };
    }

    @Override
    protected void init() {
        super.init();
        spellNameBox = new EditBox(font, 20, 20, 120, 20, Component.literal("Spell Name"));

        spellNameBox.setMaxLength(32);
        spellNameBox.setValue(editingName);

        spellNameBox.setResponder(value -> {
            editingName = value;
        });
        addRenderableWidget(spellNameBox);
        buildAvailableComponents();

        UnicornData data = Minecraft.getInstance().player.getData(PonyAttachments.UNICORN_DATA);

        spellList = new SpellList(minecraft, this, this.width/2-50, this.height/2-50, 220, 120);

        spellList.setSpells(data.spells());

        addRenderableWidget(spellList);
        for (int i = 0; i < data.spellSlots(); i++) {

            int slot = i;

            addRenderableWidget(Button.builder(Component.literal(String.valueOf(i + 1)), button -> {
                selectedSlot = slot;
                MyLittlePonyCraftingFriends.LOGGER.info("SELECTED SLOT: " + selectedSlot);}).bounds(
                                    this.width/2-50 + (i * 24),
                                    this.height/2+80,
                                    20,
                                    20
                            ).build()
            );
        }

        addRenderableWidget(Button.builder(
                                Component.literal("Create"),
                                button -> {
                                    createSpell();
                                }
                        ).bounds(
                                this.width/2-50,
                                this.height/2-100,
                                60,
                                20).build());
    }

    public void assignSpell(int spellIndex) {
        MyLittlePonyCraftingFriends.LOGGER.info("CLIENT EQUIP: slot=" + selectedSlot
                + " spell=" + spellIndex);
        PacketDistributor.sendToServer(
                new EquipSpellPayload(selectedSlot, spellIndex)
        );
    }
    public void replaceSpell(int spellIndex) {

        UnicornSpell newSpell = UnicornSpell.registerSpell(editingName,List.copyOf(editingComponents));

        PacketDistributor.sendToServer(
                new ReplaceSpellPayload(spellIndex, newSpell)
        );
    }
    private void createSpell() {
        UnicornSpell newSpell = UnicornSpell.registerSpell(editingName,List.copyOf(editingComponents));

        PacketDistributor.sendToServer(
                new CreateSpellPayload(newSpell)
        );
    }
    public void removeSpell(int spellIndex) {
        PacketDistributor.sendToServer(
                new RemoveSpellPayload(spellIndex)
        );
    }

    @Override
    public void tick() {
        super.tick();

        assert minecraft != null;
        if (minecraft.player == null) {
            return;
        }

        UnicornData current = minecraft.player.getData(PonyAttachments.UNICORN_DATA);

        if (!current.equals(lastData)) {
            lastData = current;

            spellList.setSpells(current.spells());
        }
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