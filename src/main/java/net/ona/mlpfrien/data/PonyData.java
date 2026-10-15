package net.ona.mlpfrien.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;

import java.util.List;

public record PonyData(RACES race,CUTIE_MARKS cutieMark, int maneStyle, int coatColor, int maneColor, int eyeColor, boolean discoveredCM) {

    public static PonyData defaultData() {
        return new PonyData(RACES.EARTH, CUTIE_MARKS.ERROR, 0, 0xFFF0FF, 0xF0FFFF,0xFFFFF0,false);
    }

    public enum RACES {
        EARTH(false,true,List.of(new RaceModifiers(Attributes.ARMOR,ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"earth_armor")
                ,5, AttributeModifier.Operation.ADD_VALUE),new RaceModifiers(Attributes.ATTACK_DAMAGE,ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"earth_dmg")
                ,2.5, AttributeModifier.Operation.ADD_VALUE),new RaceModifiers(Attributes.MAX_HEALTH,ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"earth_health")
                ,10, AttributeModifier.Operation.ADD_VALUE))),
        UNICORN(true,false,List.of(new RaceModifiers(Attributes.ATTACK_DAMAGE,ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"uni_damage")
                ,-0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),new RaceModifiers(Attributes.MAX_HEALTH,ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"uni_health")
                ,-4, AttributeModifier.Operation.ADD_VALUE))),
        PEGASUS(false,true,List.of(new RaceModifiers(Attributes.MOVEMENT_SPEED,ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"peg_damage")
                ,0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),new RaceModifiers(Attributes.MAX_HEALTH,ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"peg_health")
                ,-2, AttributeModifier.Operation.ADD_VALUE)));

        private final boolean canUseMagic;
        private final boolean canFly;
        private final List<RaceModifiers> raceModifiers;
        RACES(boolean canUseMagic, boolean canFly, List<RaceModifiers> raceModifiers) {
            this.canUseMagic = canUseMagic;
            this.canFly = canFly;
            this.raceModifiers = raceModifiers;
        }
        public boolean canUseMagic() {return canUseMagic;}
        public boolean canFly() {return canFly;}
        public List<RaceModifiers> getRaceModifiers() {return raceModifiers;}
    }

    public enum CUTIE_MARKS {
        ERROR,
        BLANK,
        BUILDING,
        MINING,
        EXPLORING
    }
    public record RaceModifiers(Holder<Attribute> attribute, ResourceLocation id, double amount, AttributeModifier.Operation operation){};

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
