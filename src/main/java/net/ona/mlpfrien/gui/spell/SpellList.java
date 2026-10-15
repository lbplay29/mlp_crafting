package net.ona.mlpfrien.gui.spell;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.gui.PonyCreatorGUI;
import net.ona.mlpfrien.gui.SpellManagerGUI;
import net.ona.mlpfrien.magic.UnicornSpell;
import net.ona.mlpfrien.util.TextImageButton;

import java.util.List;

public class SpellList extends ContainerObjectSelectionList<SpellList.SpellEntry> {

    private final SpellManagerGUI screen;
    private static final WidgetSprites LETTER_SPRITES = new WidgetSprites(id("textbox_letter_gui"),id("textbox_letter_gui03"),id("textbox_letter_gui02"),
            id("textbox_letter_gui03"));
    private static final WidgetSprites OPTION_SPRITES = new WidgetSprites(id("textbox_gui"),id("textbox_gui03"),id("textbox_gui02"),
            id("textbox_gui03"));
    private static final WidgetSprites CONFIRM_SPRITES = new WidgetSprites(id("textbox_confirm_gui"),id("textbox_confirm_gui03"),id("textbox_confirm_gui02"),
            id("textbox_confirm_gui03"));

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, path);
    }


    public SpellList(Minecraft minecraft, SpellManagerGUI screen, int x, int y, int width, int height) {
        super(minecraft, width, height, y, 33);

        this.screen = screen;
        setX(x);
    }

    public void setSpells(List<UnicornSpell> spells) {
        clearEntries();

        for (int i = 0; i < spells.size(); i++) {
            addEntry(new SpellEntry(screen, spells.get(i), i));
        }
    }

    public static class SpellEntry extends ContainerObjectSelectionList.Entry<SpellEntry> {
        private final TextImageButton spellButton;
        private final TextImageButton replaceButton;
        private final TextImageButton removeButton;
        private SpellManagerGUI menu;

        public SpellEntry(SpellManagerGUI screen, UnicornSpell spell, int spellIndex) {
            spellButton = new TextImageButton(0,0,86,29,OPTION_SPRITES,button -> screen.assignSpell(spellIndex),Component.literal(spell.name()),
                    screen.getMinecraft().font);

            replaceButton = new TextImageButton(0,0,56,29,CONFIRM_SPRITES,button -> screen.replaceSpell(spellIndex),Component.literal("Replace"),
                    screen.getMinecraft().font);

            removeButton = new TextImageButton(0,0,29,29,LETTER_SPRITES,button -> screen.removeSpell(spellIndex),Component.literal("X"),
                    screen.getMinecraft().font);

            menu=screen;
        }


        @Override
        public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
            spellButton.setX(left);
            spellButton.setY(top);

            replaceButton.setX(left + 124);
            replaceButton.setY(top);

            removeButton.setX(left + 183);
            removeButton.setY(top);

            spellButton.render(guiGraphics, mouseX, mouseY, partialTick);
            replaceButton.render(guiGraphics, mouseX, mouseY, partialTick);
            removeButton.render(guiGraphics, mouseX, mouseY, partialTick);
            spellButton.active = menu.hasSlot;
            replaceButton.active = menu.canCreate();
        }


        @Override
        public List<? extends GuiEventListener> children() {
            return List.of(spellButton, replaceButton, removeButton);
        }


        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of(spellButton, replaceButton, removeButton);
        }
    }
}