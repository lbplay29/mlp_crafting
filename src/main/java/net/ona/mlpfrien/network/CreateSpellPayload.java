package net.ona.mlpfrien.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.magic.UnicornSpell;

public record CreateSpellPayload(
        UnicornSpell spell
) implements CustomPacketPayload {

    public static final Type<CreateSpellPayload> TYPE =
            new Type<>(
                    ResourceLocation.fromNamespaceAndPath(
                            MyLittlePonyCraftingFriends.MODID,
                            "create_spell"
                    )
            );

    public static final StreamCodec<RegistryFriendlyByteBuf, CreateSpellPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, payload) ->
                            UnicornSpell.STREAM_CODEC.encode(
                                    buf,
                                    payload.spell()
                            ),

                    buf ->
                            new CreateSpellPayload(
                                    UnicornSpell.STREAM_CODEC.decode(buf)
                            )
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
