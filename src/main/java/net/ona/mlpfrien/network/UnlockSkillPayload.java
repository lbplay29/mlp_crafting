package net.ona.mlpfrien.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;

public record UnlockSkillPayload(ResourceLocation node) implements CustomPacketPayload {

    public static final Type<UnlockSkillPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, "unlock_skill_load"));
    public static final StreamCodec<RegistryFriendlyByteBuf, UnlockSkillPayload> STREAM_CODEC = StreamCodec.of((buf, payload) ->
    {buf.writeResourceLocation(payload.node());}, buf -> {ResourceLocation node = buf.readResourceLocation();return new UnlockSkillPayload(node);});

    @Override
    public Type<? extends CustomPacketPayload> type() {return TYPE;}
}
