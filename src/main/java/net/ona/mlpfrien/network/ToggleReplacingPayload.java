package net.ona.mlpfrien.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.PonyData;
import net.ona.mlpfrien.data.skill.SkillStateData;

public record ToggleReplacingPayload(SkillStateData data) implements CustomPacketPayload {
    public static final Type<ToggleReplacingPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, "toggle_replacing_load"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ToggleReplacingPayload> STREAM_CODEC = SkillStateData.STREAM_CODEC.map(ToggleReplacingPayload::new, ToggleReplacingPayload::data);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
