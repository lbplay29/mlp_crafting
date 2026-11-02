package net.ona.mlpfrien.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;

public record ChangeScreenPayload(ScreenType screen) implements CustomPacketPayload {
    public enum ScreenType {
        creator,
        creator_simple,
        info
    }
    public static final Type<ChangeScreenPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, "open_screen"));
    public static final StreamCodec<ByteBuf, ChangeScreenPayload> STREAM_CODEC = ByteBufCodecs.idMapper(i -> ScreenType.values()[i], ScreenType::ordinal)
            .map(ChangeScreenPayload::new, ChangeScreenPayload::screen);

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
