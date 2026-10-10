package net.ona.mlpfrien.gui.spell;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.ona.mlpfrien.gui.SpellManagerGUI;
import net.ona.mlpfrien.magic.UnicornSpell;

import java.util.List;

public class SpellList
        extends ContainerObjectSelectionList<SpellList.SpellEntry> {

    private final SpellManagerGUI screen;

    public SpellList(
            Minecraft minecraft,
            SpellManagerGUI screen,
            int x,
            int y,
            int width,
            int height
    ) {
        super(
                minecraft,
                width,
                height,
                y,
                24
        );

        this.screen = screen;

        setX(x);
    }

    public void setSpells(List<UnicornSpell> spells) {
        clearEntries();

        for (int i = 0; i < spells.size(); i++) {
            addEntry(
                    new SpellEntry(
                            screen,
                            spells.get(i),
                            i
                    )
            );
        }
    }

    public static class SpellEntry
            extends ContainerObjectSelectionList.Entry<SpellEntry> {

        private final Button spellButton;
        private final Button replaceButton;
        private final Button removeButton;

        public SpellEntry(
                SpellManagerGUI screen,
                UnicornSpell spell,
                int spellIndex
        ) {

            spellButton = Button.builder(
                    Component.literal(spell.name()),
                    button -> screen.assignSpell(spellIndex)
            ).bounds(
                    0,
                    0,
                    120,
                    20
            ).build();


            replaceButton = Button.builder(
                    Component.literal("Replace"),
                    button -> screen.replaceSpell(spellIndex)
            ).bounds(
                    0,
                    0,
                    55,
                    20
            ).build();


            removeButton = Button.builder(
                    Component.literal("X"),
                    button -> screen.removeSpell(spellIndex)
            ).bounds(
                    0,
                    0,
                    20,
                    20
            ).build();
        }


        @Override
        public void render(
                GuiGraphics guiGraphics,
                int index,
                int top,
                int left,
                int width,
                int height,
                int mouseX,
                int mouseY,
                boolean hovering,
                float partialTick
        ) {

            spellButton.setX(left);
            spellButton.setY(top);

            replaceButton.setX(left + 124);
            replaceButton.setY(top);

            removeButton.setX(left + 183);
            removeButton.setY(top);


            spellButton.render(
                    guiGraphics,
                    mouseX,
                    mouseY,
                    partialTick
            );

            replaceButton.render(
                    guiGraphics,
                    mouseX,
                    mouseY,
                    partialTick
            );

            removeButton.render(
                    guiGraphics,
                    mouseX,
                    mouseY,
                    partialTick
            );
        }


        @Override
        public List<? extends GuiEventListener> children() {
            return List.of(
                    spellButton,
                    replaceButton,
                    removeButton
            );
        }


        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of(
                    spellButton,
                    replaceButton,
                    removeButton
            );
        }
    }
}