package net.ona.mlpfrien.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.PonyData;

public record PonyDataPayload(PonyData data) implements CustomPacketPayload {
    public static final Type<PonyDataPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, "update_pony_data"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PonyDataPayload> STREAM_CODEC = PonyData.STREAM_CODEC.map(PonyDataPayload::new, PonyDataPayload::data);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
