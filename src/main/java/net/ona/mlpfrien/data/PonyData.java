package net.ona.mlpfrien.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.common.util.INBTSerializable;

public record PonyData(RACES race,CUTIE_MARKS cutieMark, int maneStyle, int coatColor, int maneColor, int eyeColor, boolean discoveredCM) {

    public static PonyData defaultData() {
        return new PonyData(RACES.EARTH, CUTIE_MARKS.ERROR, 0, 0xFFF0FF, 0xF0FFFF,0xFFFFF0,false);
    }

    public enum RACES {
        EARTH(false,true),
        UNICORN(true,false),
        PEGASUS(false,true);

        private final boolean canUseMagic;
        private final boolean canFly;
        RACES(boolean canUseMagic, boolean canFly) {
            this.canUseMagic = canUseMagic;
            this.canFly = canFly;
        }
        public boolean canUseMagic() {return canUseMagic;}
        public boolean canFly() {return canFly;}
    }

    public enum CUTIE_MARKS {
        ERROR,
        BLANK,
        BUILDING,
        MINING,
        EXPLORING
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, Integer> FRIENDLY_VAR_INT = StreamCodec.of(ByteBufCodecs.VAR_INT::encode, ByteBufCodecs.VAR_INT::decode);

    public static final StreamCodec<RegistryFriendlyByteBuf, RACES> RACE_STREAM_CODEC = FRIENDLY_VAR_INT.map(ordinal -> RACES.values()[ordinal], RACES::ordinal);
    public static final Codec<RACES> RACE_CODEC = Codec.STRING.xmap(RACES::valueOf, RACES::name);
    public static final StreamCodec<RegistryFriendlyByteBuf, CUTIE_MARKS> MARK_STREAM_CODEC = FRIENDLY_VAR_INT.map(ordinal -> CUTIE_MARKS.values()[ordinal], CUTIE_MARKS::ordinal);
    public static final Codec<CUTIE_MARKS> MARK_CODEC = Codec.STRING.xmap(CUTIE_MARKS::valueOf, CUTIE_MARKS::name);

    public static final Codec<PonyData> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    RACE_CODEC.fieldOf("race").forGetter(PonyData::race),
                    MARK_CODEC.fieldOf("cutie_mark").forGetter(PonyData::cutieMark),
                    Codec.INT.fieldOf("mane_style").forGetter(PonyData::maneStyle),
                    Codec.INT.fieldOf("coat_color").forGetter(PonyData::coatColor),
                    Codec.INT.fieldOf("mane_color").forGetter(PonyData::maneColor),
                    Codec.INT.fieldOf("eye_color").forGetter(PonyData::eyeColor),
                    Codec.BOOL.fieldOf("discoveredCM").forGetter(PonyData::discoveredCM)
            ).apply(instance, PonyData::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, PonyData> STREAM_CODEC =
            StreamCodec.of(
                    (buf, data) -> {
                        RACE_STREAM_CODEC.encode(buf, data.race());
                        MARK_STREAM_CODEC.encode(buf, data.cutieMark());
                        buf.writeVarInt(data.maneStyle());
                        buf.writeInt(data.coatColor());
                        buf.writeInt(data.maneColor());
                        buf.writeInt(data.eyeColor());
                        buf.writeBoolean(data.discoveredCM());
                    },
                    buf -> new PonyData(
                            RACE_STREAM_CODEC.decode(buf),
                            MARK_STREAM_CODEC.decode(buf),
                            buf.readVarInt(),
                            buf.readInt(),
                            buf.readInt(),
                            buf.readInt(),
                            buf.readBoolean()
                    )
            );
}
