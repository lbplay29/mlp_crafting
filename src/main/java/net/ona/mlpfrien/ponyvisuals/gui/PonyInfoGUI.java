package net.ona.mlpfrien.ponyvisuals.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.data.PonyData;
import net.ona.mlpfrien.ponyvisuals.render.CutieMarkManager;
import net.ona.mlpfrien.util.TextImageButton;

import java.util.Locale;

public class PonyInfoGUI extends Screen {
    public PonyInfoGUI() {
        super(Component.literal("boobies"));
    }
    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, path);
    }
    private static final WidgetSprites OPTION_SPRITES =
            new WidgetSprites(id("textbox_gui"), id("textbox_gui"), id("textbox_gui02"),
                    id("textbox_gui02"));

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        LocalPlayer playerR = Minecraft.getInstance().player;
        assert playerR!=null;
        PonyData data = playerR.getData(PonyAttachments.PONY_DATA);
        String raceString = data.race().toString().toLowerCase().replace(data.race().toString().toLowerCase().charAt(0),data.race().toString().toUpperCase().charAt(0));
        if (data.race().canUseMagic()) {
            addRenderableWidget(new TextImageButton(this.width/2-100,this.height/2-100,86,29,OPTION_SPRITES,button -> {
                Minecraft.getInstance().setScreen(new SpellManagerGUI());
            },Component.literal("Spells"),this.font));
        }
        guiGraphics.blit(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"textures/gui/sprites/textbox_gui.png"),
                this.width / 2 + 62, this.height / 2-46,0,0,86,29,86,29);
        guiGraphics.drawString(this.font, raceString, this.width/2+75, this.height/2-35, 0x202020,false);

    }
}
