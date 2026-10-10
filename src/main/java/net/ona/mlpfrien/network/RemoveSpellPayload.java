package net.ona.mlpfrien.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;

public record RemoveSpellPayload(
        int spellIndex
) implements CustomPacketPayload {

    public static final Type<RemoveSpellPayload> TYPE =
            new Type<>(
                    ResourceLocation.fromNamespaceAndPath(
                            MyLittlePonyCraftingFriends.MODID,
                            "remove_spell"
                    )
            );

    public static final StreamCodec<RegistryFriendlyByteBuf, RemoveSpellPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, payload) ->
                            buf.writeVarInt(payload.spellIndex()),

                    buf ->
                            new RemoveSpellPayload(
                                    buf.readVarInt()
                            )
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
