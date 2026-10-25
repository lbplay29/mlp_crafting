package net.ona.mlpfrien.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.AppearanceData;
import net.ona.mlpfrien.data.PonyData;

public record AppearanceDataPayload(AppearanceData data) implements CustomPacketPayload {
    public static final Type<AppearanceDataPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, "update_appearance_data"));
    public static final StreamCodec<RegistryFriendlyByteBuf, AppearanceDataPayload> STREAM_CODEC = AppearanceData.STREAM_CODEC.map(AppearanceDataPayload::new, AppearanceDataPayload::data);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
