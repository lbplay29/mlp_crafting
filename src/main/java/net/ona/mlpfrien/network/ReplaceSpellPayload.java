package net.ona.mlpfrien.network;

import com.mojang.datafixers.types.Type;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.magic.UnicornSpell;

public record ReplaceSpellPayload(
        int spellIndex,
        UnicornSpell spell
) implements CustomPacketPayload {

    public static final Type<ReplaceSpellPayload> TYPE =
            new Type<>(
                    ResourceLocation.fromNamespaceAndPath(
                            MyLittlePonyCraftingFriends.MODID,
                            "replace_spell"
                    )
            );

    public static final StreamCodec<RegistryFriendlyByteBuf, ReplaceSpellPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, payload) -> {
                        buf.writeVarInt(payload.spellIndex());

                        UnicornSpell.STREAM_CODEC.encode(
                                buf,
                                payload.spell()
                        );
                    },

                    buf -> new ReplaceSpellPayload(
                            buf.readVarInt(),
                            UnicornSpell.STREAM_CODEC.decode(buf)
                    )
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
