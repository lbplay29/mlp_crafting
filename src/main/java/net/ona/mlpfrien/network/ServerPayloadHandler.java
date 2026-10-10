package net.ona.mlpfrien.network;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.data.PonyData;
import net.ona.mlpfrien.magic.SpellCaster;
import net.ona.mlpfrien.magic.UnicornData;
import net.ona.mlpfrien.magic.UnicornSpell;
import net.ona.mlpfrien.magic.UnicornSpells;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
        UnicornData data = player.getData(PonyAttachments.UNICORN_DATA);
        Optional<UnicornSpell> spell = data.equippedSpells().get(payload.slot());
        if (spell.isEmpty()) {
            player.displayClientMessage(Component.literal("No spell equipped in slot "+payload.slot()), true);
            return;
        }
        if (data.mana()>=spell.get().cost()) {
            SpellCaster.castSpell(player, spell.get());
        } else {
            player.displayClientMessage(Component.literal("Not enough mana! Missing "+(spell.get().cost()-data.mana())), true);
        }
    }

    public static void handleCreateSpell(CreateSpellPayload payload, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)) {
            return;
        }

        UnicornData data = player.getData(PonyAttachments.UNICORN_DATA);

        List<UnicornSpell> spells = new ArrayList<>(data.spells());

        spells.add(payload.spell());

        player.setData(PonyAttachments.UNICORN_DATA, new UnicornData(List.copyOf(spells), data.equippedSpells(), data.spellSlots(), data.mana(), data.max_mana(), data.level()));
    }

    public static void handleReplaceSpell(ReplaceSpellPayload payload, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)) {
            return;
        }
        UnicornData data = player.getData(PonyAttachments.UNICORN_DATA);

        if (payload.spellIndex() < 0 || payload.spellIndex() >= data.spells().size()) {
            return;
        }

        List<UnicornSpell> spells = new ArrayList<>(data.spells());
        UnicornSpell oldSpell = spells.get(payload.spellIndex());
        spells.set(payload.spellIndex(), payload.spell());

        /*
         * If that spell was equipped, replace the equipped
         * version too.
         */
        List<Optional<UnicornSpell>> equipped = new ArrayList<>(data.equippedSpells());

        for (int i = 0; i < equipped.size(); i++) {
            if (equipped.get(i).filter(oldSpell::equals).isPresent()) {
                equipped.set(i, Optional.of(payload.spell()));
            }
        }

        player.setData(PonyAttachments.UNICORN_DATA,
                new UnicornData(List.copyOf(spells), List.copyOf(equipped), data.spellSlots(), data.mana(), data.max_mana(), data.level()));
    }
    public static void handleRemoveSpell(RemoveSpellPayload payload, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)) {
            return;
        }
        UnicornData data = player.getData(PonyAttachments.UNICORN_DATA);

        if (payload.spellIndex() < 0
                || payload.spellIndex() >= data.spells().size()) {
            return;
        }

        List<UnicornSpell> spells = new ArrayList<>(data.spells());
        UnicornSpell removedSpell = spells.remove(payload.spellIndex());
        List<Optional<UnicornSpell>> equipped = new ArrayList<>(data.equippedSpells());

        for (int i = 0; i < equipped.size(); i++) {
            Optional<UnicornSpell> equippedSpell = equipped.get(i);
            if (equippedSpell.isPresent() && equippedSpell.get().equals(removedSpell)) {
                equipped.set(i, Optional.empty());
            }
        }

        player.setData(PonyAttachments.UNICORN_DATA,
                new UnicornData(List.copyOf(spells), List.copyOf(equipped), data.spellSlots(), data.mana(), data.max_mana(), data.level()));
    }
    public static void handleEquipSpell(EquipSpellPayload payload, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)) {
            return;
        }
        UnicornData data = player.getData(PonyAttachments.UNICORN_DATA);

        if (payload.spellIndex() < 0 || payload.spellIndex() >= data.spells().size()) {
            return;
        }
        if (payload.slot() < 0 || payload.slot() >= data.spellSlots()) {
            return;
        }

        UnicornSpell spell = data.spells().get(payload.spellIndex());
        List<Optional<UnicornSpell>> equipped = new ArrayList<>(data.equippedSpells());
        equipped.set(payload.slot(), Optional.of(spell));

        MyLittlePonyCraftingFriends.LOGGER.info("SERVER EQUIP: slot=" + payload.slot()
                + " spell=" + payload.spellIndex());

        player.setData(PonyAttachments.UNICORN_DATA,
                new UnicornData(data.spells(), List.copyOf(equipped), data.spellSlots(), data.mana(), data.max_mana(), data.level()));
    }
}
