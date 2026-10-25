package net.ona.mlpfrien.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.data.PonyData;
import net.ona.mlpfrien.data.skill.SkillTrees;
import net.ona.mlpfrien.util.TextImageButton;

import static net.ona.mlpfrien.util.BasicUtilities.id;

public class PonyInfoGUI extends Screen {
    public PonyInfoGUI() {
        super(Component.literal("boobies"));
    }
    private static final WidgetSprites LETTER_SPRITES = new WidgetSprites(id("textbox_letter_gui"),id("textbox_letter_gui03"),id("textbox_letter_gui02"),
            id("textbox_letter_gui03"));
    private static final WidgetSprites OPTION_SPRITES = new WidgetSprites(id("textbox_gui"),id("textbox_gui03"),id("textbox_gui02"),
            id("textbox_gui03"));
    private static final WidgetSprites FAKE_OPTION_SPRITES = new WidgetSprites(id("textbox_gui"),id("textbox_gui"),id("textbox_gui"),
            id("textbox_gui"));
    private static final WidgetSprites CONFIRM_SPRITES = new WidgetSprites(id("textbox_confirm_gui"),id("textbox_confirm_gui03"),id("textbox_confirm_gui02"),
            id("textbox_confirm_gui03"));


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        LocalPlayer playerR = Minecraft.getInstance().player;
        assert playerR!=null;
        PonyData data = playerR.getData(PonyAttachments.PONY_DATA);
        String raceString = data.race().toString().toLowerCase().replace(data.race().toString().toLowerCase().charAt(0),data.race().toString().toUpperCase().charAt(0));
        raceString = raceString.equals("Earth") ? raceString+" Pony" : raceString;
        if (data.race().canUseMagic()) {
            addRenderableWidget(new TextImageButton(this.width/2-80,this.height/2-90,86,29,OPTION_SPRITES,button -> {
                Minecraft.getInstance().setScreen(new SpellManagerGUI());
            },Component.literal("Spells"),this.font));
        }
        addRenderableWidget(new TextImageButton(this.width/2+62,this.height/2-46,86,29,FAKE_OPTION_SPRITES,button -> {
        },Component.literal(raceString),this.font));
        guiGraphics.blit(id("textures/gui/cutie_mark_gui.png"),this.width/2-100,this.height/2-46,0,0,90,89,90,89);
        if (data.discoveredCM()) {
            switch (data.cutieMark()) {
                case BLANK -> guiGraphics.blit(id("textures/gui/cm_blank.png"),this.width/2-87,this.height/2-32,
                        0,0,64,64,64,64);
                case BUILDING -> guiGraphics.blit(id("textures/gui/cm_building.png"),this.width/2-87,this.height/2-32,
                        0,0,64,64,64,64);
                case MINING -> guiGraphics.blit(id("textures/gui/cm_mining.png"),this.width/2-87,this.height/2-32,
                        0,0,64,64,64,64);
                case EXPLORING -> guiGraphics.blit(id("textures/gui/cm_exploring.png"),this.width/2-87,this.height/2-32,
                        0,0,64,64,64,64);
            }
        } else {
            guiGraphics.blit(id("textures/gui/cm_blank.png"),this.width/2-87,this.height/2-32,
                    0,0,64,64,64,64);
        }
        if (!SkillTrees.getAll().stream().filter(skillTree -> skillTree.isAvailable(data)).toList().isEmpty()) {
            addRenderableWidget(new TextImageButton(this.width/2-80,this.height/2+57,56,29,CONFIRM_SPRITES,button -> {
                Minecraft.getInstance().setScreen(new SkillTreeGUI());
            },Component.literal("Skills"),this.font));
        }
    }
}
