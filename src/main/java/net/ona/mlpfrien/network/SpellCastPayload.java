package net.ona.mlpfrien.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;

public record SpellCastPayload(int slot) implements CustomPacketPayload {

    public static final Type<SpellCastPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, "spell_casting_load"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellCastPayload> STREAM_CODEC = StreamCodec.of((buf, payload) ->
            {buf.writeVarInt(payload.slot());}, buf -> {int slot = buf.readVarInt();return new SpellCastPayload(slot);});

    @Override
    public Type<? extends CustomPacketPayload> type() {return TYPE;}
}