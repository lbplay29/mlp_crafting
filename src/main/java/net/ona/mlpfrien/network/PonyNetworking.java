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
    }
}
