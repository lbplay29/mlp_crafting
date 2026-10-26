package net.ona.mlpfrien.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class PonyNetworking {

    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(
                PonyDataPayload.TYPE,
                PonyDataPayload.STREAM_CODEC,
                ServerPayloadHandler::handlePonyData
        );

        registrar.playToServer(
                PegasusFlightPayload.TYPE,
                PegasusFlightPayload.STREAM_CODEC,
                ServerPayloadHandler::handlePegasusFlight
        );

        registrar.playToServer(
                SpellCastPayload.TYPE,
                SpellCastPayload.STREAM_CODEC,
                ServerPayloadHandler::handleSpellCasting
        );

        registrar.playToServer(
                CreateSpellPayload.TYPE,
                CreateSpellPayload.STREAM_CODEC,
                ServerPayloadHandler::handleCreateSpell
        );

        registrar.playToServer(
                ReplaceSpellPayload.TYPE,
                ReplaceSpellPayload.STREAM_CODEC,
                ServerPayloadHandler::handleReplaceSpell
        );

        registrar.playToServer(
                RemoveSpellPayload.TYPE,
                RemoveSpellPayload.STREAM_CODEC,
                ServerPayloadHandler::handleRemoveSpell
        );

        registrar.playToServer(
                EquipSpellPayload.TYPE,
                EquipSpellPayload.STREAM_CODEC,
                ServerPayloadHandler::handleEquipSpell
        );

        registrar.playToServer(
                AppearanceDataPayload.TYPE,
                AppearanceDataPayload.STREAM_CODEC,
                ServerPayloadHandler::handleAppearanceData
        );

        registrar.playToServer(
                UnlockSkillPayload.TYPE,
                UnlockSkillPayload.STREAM_CODEC,
                ServerPayloadHandler::handleUnlockSkill
        );

        registrar.playToServer(
                ToggleReplacingPayload.TYPE,
                ToggleReplacingPayload.STREAM_CODEC,
                ServerPayloadHandler::handleToggle
        );
    }
}
