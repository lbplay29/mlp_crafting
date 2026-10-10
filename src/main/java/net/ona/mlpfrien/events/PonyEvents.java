package net.ona.mlpfrien.events;

import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDestroyBlockEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.data.PonyData;
import net.ona.mlpfrien.magic.DelayedSpellManager;
import net.ona.mlpfrien.magic.SpellCaster;
import net.ona.mlpfrien.magic.UnicornData;

@EventBusSubscriber(modid = MyLittlePonyCraftingFriends.MODID)
public class PonyEvents {

    @SubscribeEvent
    public static void playerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        int distance = player.getStats().getValue(Stats.CUSTOM.get(Stats.SPRINT_ONE_CM));
        PonyData ponyData = player.getData(PonyAttachments.PONY_DATA);
        UnicornData unicornData = player.getData(PonyAttachments.UNICORN_DATA);
        if (ponyData.cutieMark()== PonyData.CUTIE_MARKS.EXPLORING) {
            if (distance>=2000) {
                if (!ponyData.discoveredCM()) {
                    event.getEntity().setData(PonyAttachments.PONY_DATA, new PonyData(ponyData.race(), ponyData.cutieMark(), ponyData.coatColor(), ponyData.maneStyle(),
                            ponyData.maneColor(), ponyData.eyeColor(), true));
                }
            }
        }
        if (unicornData.mana()<unicornData.max_mana()) {
            SpellCaster.useMana(player,-1);
        }

    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("mlp").then(Commands.literal("data").then(Commands.literal("reset")
                                .executes(context -> {
                                    ServerPlayer player = context.getSource().getPlayerOrException();

                                    player.setData(PonyAttachments.PONY_DATA, PonyData.defaultData());
                                    player.setData(PonyAttachments.UNICORN_DATA, UnicornData.defaultData());

                                    return 1;})
                                )
                        )
        );
    }

    @SubscribeEvent
    public static void blockBroken(LivingDestroyBlockEvent event) {
        PonyData data = event.getEntity().getData(PonyAttachments.PONY_DATA);
        if (data.cutieMark()== PonyData.CUTIE_MARKS.MINING) {
            if (event.getState().is(BlockTags.DIAMOND_ORES)) {
                if (!data.discoveredCM()) {
                    event.getEntity().setData(PonyAttachments.PONY_DATA, new PonyData(data.race(),data.cutieMark(), data.coatColor(), data.maneStyle(),
                            data.maneColor(), data.eyeColor(), true));
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
                    event.getEntity().setData(PonyAttachments.PONY_DATA, new PonyData(data.race(),data.cutieMark(), data.maneStyle(), data.coatColor(),
                            data.maneColor(), data.eyeColor(), true));
                }
            }
        }
    }

    @SubscribeEvent
    public static void serverTick(ServerTickEvent.Post event) {
        DelayedSpellManager.tick();
    }
}
