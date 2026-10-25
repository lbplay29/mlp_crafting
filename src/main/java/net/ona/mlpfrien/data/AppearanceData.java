package net.ona.mlpfrien.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record AppearanceData(int maneStyle, int coatColor, int maneColor, int eyeColor) {
    public static AppearanceData defaultData() {
        return new AppearanceData(0, 0xFFF0FF, 0xF0FFFF,0xFFFFF0);
    }

    public static final Codec<AppearanceData> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.INT.fieldOf("mane_style").forGetter(AppearanceData::maneStyle),
                    Codec.INT.fieldOf("coat_color").forGetter(AppearanceData::coatColor),
                    Codec.INT.fieldOf("mane_color").forGetter(AppearanceData::maneColor),
                    Codec.INT.fieldOf("eye_color").forGetter(AppearanceData::eyeColor)
            ).apply(instance, AppearanceData::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, AppearanceData> STREAM_CODEC =
            StreamCodec.of(
                    (buf, data) -> {
                        buf.writeVarInt(data.maneStyle());
                        buf.writeInt(data.coatColor());
                        buf.writeInt(data.maneColor());
                        buf.writeInt(data.eyeColor());
                    },
                    buf -> new AppearanceData(
                            buf.readVarInt(),
                            buf.readInt(),
                            buf.readInt(),
                            buf.readInt()
                    )
            );
}
