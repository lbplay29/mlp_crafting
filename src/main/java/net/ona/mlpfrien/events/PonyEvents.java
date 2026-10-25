package net.ona.mlpfrien.events;

import net.minecraft.commands.Commands;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.*;
import net.ona.mlpfrien.init.PonyTriggers;
import net.ona.mlpfrien.magic.DelayedSpellManager;
import net.ona.mlpfrien.magic.SpellCaster;
import net.ona.mlpfrien.util.PonyAttributeManager;

@EventBusSubscriber(modid = MyLittlePonyCraftingFriends.MODID)
public class PonyEvents {

    @SubscribeEvent
    public static void playerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        int distance = player.getStats().getValue(Stats.CUSTOM.get(Stats.SPRINT_ONE_CM));
        PonyData data = player.getData(PonyAttachments.PONY_DATA);
        UnicornData unicornData = player.getData(PonyAttachments.UNICORN_DATA);
        if (data.cutieMark()== PonyData.CUTIE_MARKS.EXPLORING) {
            if (distance>=100000) {
                if (!data.discoveredCM()) {
                    discoverMark(data, (ServerPlayer) event.getEntity());
                }
            }
        }
        if (unicornData.mana()<unicornData.max_mana()) {
            SpellCaster.useMana(player,-0.01f);
        }

    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("mlp").then(Commands.literal("data").then(Commands.literal("reset").executes(context -> {
                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                    player.setData(PonyAttachments.PONY_DATA, PonyData.defaultData());
                                    player.setData(PonyAttachments.UNICORN_DATA, UnicornData.defaultData());
                                    player.setData(PonyAttachments.APPEARANCE_DATA, AppearanceData.defaultData());
                                    player.setData(PonyAttachments.LEVEL_DATA, LevelData.defaultData());
                                    return 1;}))));
    }

    @SubscribeEvent
    public static void playerEaten(LivingEntityUseItemEvent.Finish event) {
        if (!event.getItem().has(DataComponents.FOOD)) {
            return;
        }
        if (event.getEntity().getData(PonyAttachments.PONY_DATA).race().canUseMagic()) {
            UnicornData data = event.getEntity().getData(PonyAttachments.UNICORN_DATA);
            float heal = event.getItem().getFoodProperties(event.getEntity()).nutrition() *10;
            event.getEntity().setData(PonyAttachments.UNICORN_DATA, new UnicornData(data.spells(),data.equippedSpells(), data.spellSlots(),
                    Math.min(data.mana()+heal, data.max_mana()), data.max_mana(), data.level()));
        }
    }

    @SubscribeEvent
    public static void blockBroken(BlockEvent.BreakEvent event) {
        PonyData data = event.getPlayer().getData(PonyAttachments.PONY_DATA);
        if (data.cutieMark()== PonyData.CUTIE_MARKS.MINING) {
            if (event.getState().is(BlockTags.DIAMOND_ORES)) {
                if (!data.discoveredCM()) {
                    discoverMark(data, (ServerPlayer) event.getPlayer());
                }
            }
        }
    }

    @SubscribeEvent
    public static void blockPlaced(BlockEvent.EntityPlaceEvent event) {
        PonyData data = event.getEntity().getData(PonyAttachments.PONY_DATA);
        if (data.cutieMark()== PonyData.CUTIE_MARKS.BUILDING) {
            if (event.getState().is(BlockTags.LOGS)) {
                if (!data.discoveredCM()) {
                    discoverMark(data, (ServerPlayer) event.getEntity());
                }
            }
        }
    }

    @SubscribeEvent
    public static void playerExp(PlayerXpEvent.PickupXp event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {return;}
        LevelData data = player.getData(PonyAttachments.LEVEL_DATA);
        if (data.skillXp()>=100) {
            player.setData(PonyAttachments.LEVEL_DATA,new LevelData(data.skills(), data.skillPoints()+2, 0));
            player.playNotifySound(SoundEvents.BEACON_ACTIVATE, SoundSource.MASTER, 10f, 1f);
            player.displayClientMessage(Component.literal("Level up! +2 Skill points"),true);
        } else {
            player.setData(PonyAttachments.LEVEL_DATA,new LevelData(data.skills(), data.skillPoints(), data.skillXp()+event.getOrb().getValue()));
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {return;}
        PonyAttributeManager.applyModifiers(player.getData(PonyAttachments.PONY_DATA),player);
    }

    public static void discoverMark(PonyData data, ServerPlayer player) {
        player.setData(PonyAttachments.PONY_DATA, new PonyData(data.race(),data.cutieMark(), true));
        PonyTriggers.CUTIE_MARK_TRIGGER.get().trigger(player);
    }

    @SubscribeEvent
    public static void serverTick(ServerTickEvent.Post event) {
        DelayedSpellManager.tick();
    }
}
