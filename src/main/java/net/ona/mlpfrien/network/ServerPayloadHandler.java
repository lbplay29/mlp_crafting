package net.ona.mlpfrien.network;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.data.PonyData;
import net.ona.mlpfrien.magic.SpellCaster;
import net.ona.mlpfrien.magic.UnicornSpell;
import net.ona.mlpfrien.magic.UnicornSpells;

public class ServerPayloadHandler {

    public static void handlePonyData(PonyDataPayload payload, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)) {
            return;
        }
        PonyData newData = payload.data();
        player.setData(PonyAttachments.PONY_DATA, newData);
    }

    public static void handlePegasusFlight(PegasusFlightPayload payload, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)) {
            return;
        }
        if (!player.getData(PonyAttachments.PONY_DATA).race().canFly()||player.onGround()||player.isPassenger()) {
            return;
        }

        if (player.isFallFlying()) {
            player.setDeltaMovement(player.getDeltaMovement().x, player.getDeltaMovement().y + 1.0D, player.getDeltaMovement().z);
            player.hurtMarked = true;
        } else {
            player.startFallFlying();
        }
    }

    public static void handleSpellCasting(SpellCastPayload payload, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)) {
            return;
        }
        if (!player.getData(PonyAttachments.PONY_DATA).race().canUseMagic()) {
            return;
        }
        UnicornSpell spell = payload.data().equippedSpells().get(payload.data().currentSlot());
        if (payload.data().mana()>=spell.cost()) {
            SpellCaster.castSpell(player, spell);
        } else {
            player.displayClientMessage(Component.literal("Not enough mana! Missing "+(spell.cost()-payload.data().mana())), true);
        }

    }
}
