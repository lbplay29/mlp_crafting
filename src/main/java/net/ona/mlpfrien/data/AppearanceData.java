package net.ona.mlpfrien.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.List;

public record AppearanceData(int maneStyle, List<Integer> colors) {
    public static AppearanceData defaultData() {
        return new AppearanceData(0, List.of(0xFFF0FF, 0xF0FFFF,0xFFFFF0,0xFF00DD));
    }

    public static final Codec<AppearanceData> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.INT.fieldOf("mane_style").forGetter(AppearanceData::maneStyle),
                    Codec.list(Codec.INT).fieldOf("colors").forGetter(AppearanceData::colors)
            ).apply(instance, AppearanceData::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, AppearanceData> STREAM_CODEC =
            StreamCodec.of(
                    (buf, data) -> {
                        buf.writeVarInt(data.maneStyle());
                        buf.writeInt(data.colors().size());
                        for (int i = 0; i < data.colors().size(); i++) {
                            buf.writeVarInt(data.colors().get(i));
                        }
                    },
                    buf -> {
                        int style = buf.readVarInt();
                        int size = buf.readInt();
                        List<Integer> colors = new ArrayList<>();
                        for (int i =0; i<size;i++) {
                            colors.add(buf.readVarInt());
                        }
                        return new AppearanceData(style,colors);
                    }
            );
}
