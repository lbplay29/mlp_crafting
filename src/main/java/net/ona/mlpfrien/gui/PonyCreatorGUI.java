package net.ona.mlpfrien.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.AppearanceData;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.data.PonyData;
import net.ona.mlpfrien.network.AppearanceDataPayload;
import net.ona.mlpfrien.network.PonyDataPayload;
import net.ona.mlpfrien.ponyvisuals.render.CutieMarkManager;
import net.ona.mlpfrien.ponyvisuals.render.PonyTextureManager;
import net.ona.mlpfrien.util.TextImageButton;
import net.ona.mlpfrien.util.BasicUtilities;

import static net.ona.mlpfrien.util.BasicUtilities.id;

public class PonyCreatorGUI extends Screen {
    public PonyCreatorGUI() {
        super(Component.literal("Pony Info"));
    }
    private enum PAGES {
        RACE,
        CREATOR,
        MARK
    }
    private enum TABS {
        body,
        mane,
        other
    }
    private PonyData ponyData;
    private AppearanceData appearanceData;
    private Player player;
    private PonyData.RACES selectedRace;
    private ColorPickerWidget coatPicker;
    private ColorPickerWidget manePicker;
    private ColorPickerWidget eyePicker;
    private PAGES thisPage;
    private TextImageButton maneStyleButton;

    @Override
    protected void init() {
        super.init();
        LocalPlayer playerInit = Minecraft.getInstance().player;
        assert playerInit != null;
        player = playerInit;
        ponyData = player.getData(PonyAttachments.PONY_DATA);
        appearanceData = player.getData(PonyAttachments.APPEARANCE_DATA);
        selectedRace = ponyData.race();

        choosePage(PAGES.RACE);
    }
    private static final WidgetSprites LETTER_SPRITES = new WidgetSprites(id("textbox_letter_gui"),id("textbox_letter_gui03"),id("textbox_letter_gui02"),
            id("textbox_letter_gui03"));
    private static final WidgetSprites OPTION_SPRITES = new WidgetSprites(id("textbox_gui"),id("textbox_gui03"),id("textbox_gui02"),
            id("textbox_gui03"));
    private static final WidgetSprites CONFIRM_SPRITES = new WidgetSprites(id("textbox_confirm_gui"),id("textbox_confirm_gui03"),id("textbox_confirm_gui02"),
            id("textbox_confirm_gui03"));
    private static final ResourceLocation ColorBoxSprite = id("textures/gui/colorbox_gui.png");
    private static final ResourceLocation DescriptionSprite = id("textures/gui/description_gui.png");

    private void choosePage(PAGES curPage) {
        switch (curPage) {
            case RACE -> buildPageOne();
            case CREATOR -> buildPageTwo();
            case MARK -> buildPageThree();
        }
        thisPage=curPage;
    }

    private void buildPageOne() {
        clearWidgets();

        this.addRenderableWidget(new TextImageButton(this.width / 2 +70, this.height / 2 + 80, 56, 29, CONFIRM_SPRITES, button -> {
            choosePage(PAGES.CREATOR);}, Component.literal("Confirm"), this.font));
        this.addRenderableWidget(new TextImageButton(this.width / 2 -175, this.height / 2 - 10, 86, 29, OPTION_SPRITES, button ->{
            selectedRace= PonyData.RACES.PEGASUS;
            updateTextureP(player, withRace(selectedRace,ponyData));}, Component.literal("Pegasus"), this.font));
        this.addRenderableWidget(new TextImageButton(this.width / 2 -175, this.height / 2 + 25, 86, 29, OPTION_SPRITES, button ->{
            selectedRace= PonyData.RACES.UNICORN;
            updateTextureP(player, withRace(selectedRace,ponyData));}, Component.literal("Unicorn"), this.font));
        this.addRenderableWidget(new TextImageButton(this.width / 2 -175, this.height / 2 - 45, 86, 29, OPTION_SPRITES, button ->{
            selectedRace= PonyData.RACES.EARTH;
            updateTextureP(player, withRace(selectedRace,ponyData));}, Component.literal("Earth Pony"), this.font));
    }

    private void buildPageTwo() {
        clearWidgets();
        this.addRenderableWidget(new TextImageButton(this.width / 2 -60, this.height / 2 - 90, 86, 29, OPTION_SPRITES, button ->{
            updateTab(TABS.body);}, Component.literal("Body"), this.font));
        this.addRenderableWidget(new TextImageButton(this.width / 2 +30, this.height / 2 - 90, 86, 29, OPTION_SPRITES, button ->{
            updateTab(TABS.mane);}, Component.literal("Mane"), this.font));
        this.addRenderableWidget(new TextImageButton(this.width / 2 +120, this.height / 2 - 90, 86, 29, OPTION_SPRITES, button ->{
            updateTab(TABS.other);}, Component.literal("Eyes"), this.font));
        maneStyleButton = new TextImageButton(this.width / 2 +60, this.height / 2 +100, 86, 29, OPTION_SPRITES, button ->{
            updateTextureA(player, withManeStyle(BasicUtilities
                    .loopingInt(5,appearanceData.maneStyle()),appearanceData));}, Component.literal("Mane Style: "+appearanceData.maneStyle()), this.font);
        this.addRenderableWidget(maneStyleButton);

        coatPicker = new ColorPickerWidget(this.width / 2 + 62, this.height / 2-46, 120, 70, appearanceData.coatColor(),
                newColor -> {updateTextureA(player, withCoatColor(newColor,appearanceData));});
        manePicker = new ColorPickerWidget(this.width / 2 + 62, this.height / 2-46, 120, 70, appearanceData.maneColor(),
                newColor -> {updateTextureA(player, withManeColor(newColor,appearanceData));});
        eyePicker = new ColorPickerWidget(this.width / 2 + 62, this.height / 2-46, 120, 70, appearanceData.eyeColor(),
                newColor -> {updateTextureA(player, withEyeColor(newColor,appearanceData));});
        updateTab(TABS.body);

        this.addRenderableWidget(manePicker);
        this.addRenderableWidget(coatPicker);
        this.addRenderableWidget(eyePicker);
        this.addRenderableWidget(new TextImageButton(this.width / 2 +175, this.height / 2 + 100, 56, 29, CONFIRM_SPRITES, button -> {
            choosePage(PAGES.MARK);}, Component.literal("Confirm"), this.font));
    }

    private void buildPageThree() {
        clearWidgets();

        this.addRenderableWidget(new TextImageButton(this.width / 2 +70, this.height / 2 + 80, 56, 29, CONFIRM_SPRITES, button -> {
            CutieMarkManager.discoverCutieMarkServer(player, false);
            this.onClose();}, Component.literal("Confirm"), this.font));
        this.addRenderableWidget(new TextImageButton(this.width / 2 -175, this.height / 2 - 10, 86, 29, OPTION_SPRITES, button ->{
            CutieMarkManager.changeCutieMark(player, PonyData.CUTIE_MARKS.MINING);}, Component.literal("Mining"), this.font));
        this.addRenderableWidget(new TextImageButton(this.width / 2 -175, this.height / 2 + 25, 86, 29, OPTION_SPRITES, button ->{
            CutieMarkManager.changeCutieMark(player, PonyData.CUTIE_MARKS.EXPLORING);}, Component.literal("Exploring"), this.font));
        this.addRenderableWidget(new TextImageButton(this.width / 2 -175, this.height / 2 - 45, 86, 29, OPTION_SPRITES, button ->{
            CutieMarkManager.changeCutieMark(player, PonyData.CUTIE_MARKS.BUILDING);}, Component.literal("Building"), this.font));
    }

    private Component raceDescription() {
        return switch (selectedRace) {
            case UNICORN -> Component.literal("Unicorn\n\nEquipped with a magical horn, casting spells to gain an edge over others.");
            case PEGASUS -> Component.literal("Pegasus\n\nWith a light frame and powerful wings, soar through the skies.");
            case EARTH -> Component.literal("Earth Pony\n\nBuilt sturdy and close to nature, surrounding plants even grow faster.");
        };
    }

    private void updateTab(TABS newTab) {
        coatPicker.visible = newTab==TABS.body;
        coatPicker.active = newTab==TABS.body;

        manePicker.visible = newTab==TABS.mane;
        manePicker.active = newTab==TABS.mane;

        eyePicker.visible = newTab==TABS.other;
        eyePicker.active = newTab==TABS.other;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        LocalPlayer playerRender = Minecraft.getInstance().player;
        assert playerRender!=null;
        player = playerRender;
        ponyData = player.getData(PonyAttachments.PONY_DATA);
        appearanceData = player.getData(PonyAttachments.APPEARANCE_DATA);

        switch (thisPage) {
            case RACE -> {
                guiGraphics.blit(DescriptionSprite,this.width / 2 + 62, this.height / 2-46,0,0,164,111,164,111);

                int textY = this.height/2-35;
                int maxWidth = 140;

                for (FormattedCharSequence line : this.font.split(raceDescription(), maxWidth)) {
                    guiGraphics.drawString(this.font, line, this.width/2+75, textY, 0x202020,false);
                    textY += this.font.lineHeight;
                }
            }
        }

        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, this.width / 2 - 90 , this.height / 2 - 90, this.width / 2 + 90,
                this.height / 2 + 90, 50, 0.0625F, mouseX, mouseY, player);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

        switch (thisPage) {
            case CREATOR -> {
                guiGraphics.blit(ColorBoxSprite,this.width / 2 + 53, this.height / 2-54,0,0,138,101,138,101);
            }
        }
    }

    private void updateText(AppearanceData data) {
        if (!(maneStyleButton ==null)) {
            removeWidget(maneStyleButton);
            maneStyleButton.setMessage(Component.literal("Mane Style: "+data.maneStyle()));
            addRenderableWidget(maneStyleButton);
        }
    }

    public void updateTextureP(Player player, PonyData data) {
        PacketDistributor.sendToServer(new PonyDataPayload(data));
        PonyTextureManager.createTexture((AbstractClientPlayer) player);
    }
    public void updateTextureA(Player player, AppearanceData data) {
        PacketDistributor.sendToServer(new AppearanceDataPayload(data));
        PonyTextureManager.createTexture((AbstractClientPlayer) player);
        updateText(data);
    }

    public PonyData withRace(PonyData.RACES newRace, PonyData curData) {
        return new PonyData(newRace, curData.cutieMark(),curData.discoveredCM());
    }
    public AppearanceData withManeStyle(int newStyle, AppearanceData curData) {
        return new AppearanceData(newStyle, curData.coatColor(), curData.maneColor(), curData.eyeColor());
    }
    public AppearanceData withCoatColor(int newCoat, AppearanceData curData) {
        return new AppearanceData(curData.maneStyle(), newCoat, curData.maneColor(), curData.eyeColor());
    }
    public AppearanceData withManeColor(int newMane, AppearanceData curData) {
        return new AppearanceData(curData.maneStyle(), curData.coatColor(), newMane, curData.eyeColor());
    }
    public AppearanceData withEyeColor(int newEye, AppearanceData curData) {
        return new AppearanceData(curData.maneStyle(), curData.coatColor(), curData.maneColor(), newEye);
    }
}
