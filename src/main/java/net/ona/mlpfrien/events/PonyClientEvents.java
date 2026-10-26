package net.ona.mlpfrien.events;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.network.PacketDistributor;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.data.PonyData;
import net.ona.mlpfrien.init.PonyEntities;
import net.ona.mlpfrien.magic.ManaHud;
import net.ona.mlpfrien.network.PegasusFlightPayload;
import net.ona.mlpfrien.network.PonyDataPayload;
import net.ona.mlpfrien.network.SpellCastPayload;
import net.ona.mlpfrien.gui.PonyCreatorGUI;
import net.ona.mlpfrien.gui.PonyInfoGUI;
import net.ona.mlpfrien.network.ToggleReplacingPayload;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = MyLittlePonyCraftingFriends.MODID, value = Dist.CLIENT)
public class PonyClientEvents {
    public static final KeyMapping CREATOR_MENU = new KeyMapping(
            "key.mlpfrien.menu",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_O,
            "key.categories.mlpfrien"
    );
    public static final KeyMapping ABILITY = new KeyMapping(
            "key.mlpfrien.skill",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "key.categories.mlpfrien"
    );public static final KeyMapping ABILITY_TWO = new KeyMapping(
            "key.mlpfrien.skill_two",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            "key.categories.mlpfrien"
    );public static final KeyMapping ABILITY_THREE = new KeyMapping(
            "key.mlpfrien.skill_three",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_C,
            "key.categories.mlpfrien"
    );


    public static boolean wasJumping;
    public static boolean flightArmed;
    public static int cooldown=0;

    private static final ResourceLocation MANA_LAYER = ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, "mana_bar");

    @SubscribeEvent
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.FOOD_LEVEL, MANA_LAYER, ManaHud::render);
    }

    @SubscribeEvent
    public static void register(RegisterKeyMappingsEvent event) {
        event.register(CREATOR_MENU);
        event.register(ABILITY);
        event.register(ABILITY_TWO);
        event.register(ABILITY_THREE);
    }

    @SubscribeEvent
    public static void clientTick(ClientTickEvent.Post event) {
        Player player = Minecraft.getInstance().player;
        if (player==null) {return;}
        if (PonyClientEvents.CREATOR_MENU.consumeClick()) {
            if (player.getData(PonyAttachments.PONY_DATA).cutieMark()== PonyData.CUTIE_MARKS.ERROR) {
                Minecraft.getInstance().setScreen(new PonyCreatorGUI());
            } else {
                Minecraft.getInstance().setScreen(new PonyInfoGUI());
            }
        }
        if (ABILITY.consumeClick()) {
            PacketDistributor.sendToServer(new SpellCastPayload(0));
        } else if (ABILITY_TWO.consumeClick()) {
            PacketDistributor.sendToServer(new SpellCastPayload(1));
        }
        if (ABILITY_THREE.consumeClick()) {
            PacketDistributor.sendToServer(new ToggleReplacingPayload(player.getData(PonyAttachments.SKILL_STATE_DATA)));
        }
        if (player.getData(PonyAttachments.PONY_DATA).race() == PonyData.RACES.PEGASUS) {
            boolean jumpDown = ABILITY.isDown();
            if (player.verticalCollisionBelow) {
                flightArmed = false;
            } else if (!jumpDown) {
                flightArmed= true;
            }
            if (flightArmed && !wasJumping && jumpDown && cooldown<=0 && !player.isInWater()) {
                PacketDistributor.sendToServer(new PegasusFlightPayload());
                cooldown = 60;
            }
            wasJumping = jumpDown;
        }
        if (cooldown>0) {
            cooldown--;
        }

    }

    @SubscribeEvent
    public static void playerDeath(ClientPlayerNetworkEvent.Clone event) {
        event.getNewPlayer().setScore(event.getOldPlayer().getScore());
        PacketDistributor.sendToServer(new PonyDataPayload(event.getOldPlayer().getData(PonyAttachments.PONY_DATA)));
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(PonyEntities.UNICORN_SPELL_PROJECTILE.get(), NoopRenderer::new);
    }
}
