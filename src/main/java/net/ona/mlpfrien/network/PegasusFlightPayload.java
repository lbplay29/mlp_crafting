package net.ona.mlpfrien.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;

public record PegasusFlightPayload() implements CustomPacketPayload {

    public static final Type<PegasusFlightPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, "pegasus_flight"));

    public static final StreamCodec<ByteBuf, PegasusFlightPayload> STREAM_CODEC = StreamCodec.unit(new PegasusFlightPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {return TYPE;}
}