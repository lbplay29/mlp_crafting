package net.ona.mlpfrien.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;

public record EquipSpellPayload(
        int slot,
        int spellIndex
) implements CustomPacketPayload {

    public static final Type<EquipSpellPayload> TYPE =
            new Type<>(
                    ResourceLocation.fromNamespaceAndPath(
                            MyLittlePonyCraftingFriends.MODID,
                            "equip_spell"
                    )
            );

    public static final StreamCodec<RegistryFriendlyByteBuf, EquipSpellPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, payload) -> {
                        buf.writeVarInt(payload.slot());
                        buf.writeVarInt(payload.spellIndex());
                    },

                    buf -> new EquipSpellPayload(
                            buf.readVarInt(),
                            buf.readVarInt()
                    )
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
