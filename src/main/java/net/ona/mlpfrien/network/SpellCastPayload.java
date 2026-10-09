package net.ona.mlpfrien.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.magic.UnicornData;

public record SpellCastPayload(UnicornData data) implements CustomPacketPayload {

    public static final Type<SpellCastPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, "spell_casting_load"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellCastPayload> STREAM_CODEC = UnicornData.STREAM_CODEC.map(SpellCastPayload::new,SpellCastPayload::data);

    @Override
    public Type<? extends CustomPacketPayload> type() {return TYPE;}
}