package net.ona.mlpfrien.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.gui.spell.SpellList;
import net.ona.mlpfrien.magic.SpellComponent;
import net.ona.mlpfrien.data.UnicornData;
import net.ona.mlpfrien.magic.UnicornSpell;
import net.ona.mlpfrien.network.CreateSpellPayload;
import net.ona.mlpfrien.network.EquipSpellPayload;
import net.ona.mlpfrien.network.RemoveSpellPayload;
import net.ona.mlpfrien.network.ReplaceSpellPayload;
import net.ona.mlpfrien.util.TextImageButton;

import java.util.ArrayList;
import java.util.List;

import static net.ona.mlpfrien.util.BasicUtilities.id;

public class SpellManagerGUI extends Screen {
    public SpellManagerGUI() {
        super(Component.literal("penor"));
    }

    private Player player;
    private final List<SpellComponent> editingComponents = new ArrayList<>();
    private final List<AbstractWidget> spellComponentButtons = new ArrayList<>();
    private String editingName = "";
    private int selectedSlot = 0;
    private EditBox spellNameBox;
    private SpellList spellList;
    public boolean hasSlot;
    private TextImageButton createButton;
    private final List<TextImageButton> slotButtons = new ArrayList<>();
    private UnicornData lastData;
    private static final WidgetSprites LETTER_SPRITES = new WidgetSprites(id("textbox_letter_gui"),id("textbox_letter_gui03"),id("textbox_letter_gui02"),
            id("textbox_letter_gui03"));
    private static final WidgetSprites OPTION_SPRITES = new WidgetSprites(id("textbox_gui"),id("textbox_gui03"),id("textbox_gui02"),
            id("textbox_gui03"));
    private static final WidgetSprites CONFIRM_SPRITES = new WidgetSprites(id("textbox_confirm_gui"),id("textbox_confirm_gui03"),id("textbox_confirm_gui02"),
            id("textbox_confirm_gui03"));

    public boolean canCreate() {
        return !editingComponents.isEmpty() && !spellNameBox.getValue().isBlank();
    }

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
    private Component getTooltipComponent(SpellComponent component) {
        return switch (component) {
            case DAMAGE -> Component.literal("Damage - Deals damage to a target");
            case TELEPORT -> Component.literal("Teleport - Teleports the caster to a target");
            case AMPLIFY -> Component.literal("Amplify - Increases the effect of the next component");
            case WAIT -> Component.literal("Wait - Delays the next component by a second");
            case FIRE -> Component.literal("Fire - Sets fire to a target");
            case SELF -> Component.literal("Self - Sets the target to the caster");
            case PROJECTILE -> Component.literal("Projectile - Fires a beam that can hit a target");
            case TOUCH -> Component.literal("Touch - Sets the target to the caster's looking position");
            case EXPLODE -> Component.literal("Explode - Triggers an explosion at the target");
            case HEAL -> Component.literal("Heal - Heals a target");
        };
    }

    @Override
    protected void init() {
        super.init();
        player = Minecraft.getInstance().player;
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
            slotButtons.add(new TextImageButton(this.width/2-50 + (i * 33), this.height/2+80, 29, 29,LETTER_SPRITES,button -> {
                selectedSlot = slot;
                hasSlot = true;},Component.literal(String.valueOf(i+1)),this.font));
            addRenderableWidget(slotButtons.get(i));
        }
        createButton = new TextImageButton(this.width/2-50, this.height/2-100, 56, 29,CONFIRM_SPRITES,
                button -> createSpell(),Component.literal("Create"),this.font);
        addRenderableWidget(createButton);
    }

    public void assignSpell(int spellIndex) {
        MyLittlePonyCraftingFriends.LOGGER.info("CLIENT EQUIP: slot=" + selectedSlot + " spell=" + spellIndex);
        hasSlot=false;
        PacketDistributor.sendToServer(new EquipSpellPayload(selectedSlot, spellIndex));
    }
    public void replaceSpell(int spellIndex) {
        if (canCreate()) {
            UnicornSpell newSpell = UnicornSpell.registerSpell(editingName,List.copyOf(editingComponents));
            PacketDistributor.sendToServer(new ReplaceSpellPayload(spellIndex, newSpell));
        }
    }
    private void createSpell() {
        if (canCreate()) {
            UnicornSpell newSpell = UnicornSpell.registerSpell(editingName,List.copyOf(editingComponents));
            PacketDistributor.sendToServer(new CreateSpellPayload(newSpell));
        }
    }
    public void removeSpell(int spellIndex) {
        PacketDistributor.sendToServer(new RemoveSpellPayload(spellIndex));
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
        createButton.active=canCreate();
        for (TextImageButton slotButton : slotButtons) {
            slotButton.active = !spellList.children().isEmpty();
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
                if (editingComponents.size()<6) {
                    editingComponents.add(component);
                    rebuildSpellComponentButtons();
                } else {
                    player.displayClientMessage(Component.literal("Spell can't contain more than 6 components"),true);
                }}, Component.literal(component.name()));
            button.setTooltip(Tooltip.create(getTooltipComponent(component)));
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
            button.setTooltip(Tooltip.create(getTooltipComponent(component)));
            addRenderableWidget(button);
            spellComponentButtons.add(button);
        }
    }
}