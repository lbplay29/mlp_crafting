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
import net.ona.mlpfrien.data.skill.PonySkills;

import javax.annotation.Nullable;
import java.util.List;

import static net.ona.mlpfrien.util.BasicUtilities.id;

public record PonyData(RACES race,CUTIE_MARKS cutieMark, boolean discoveredCM) {

    public static PonyData defaultData() {
        return new PonyData(RACES.EARTH, CUTIE_MARKS.ERROR, false);
    }

    public enum RACES {
        EARTH(false,false,List.of(new AttModifiers(Attributes.ARMOR,ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"earth_armor")
                ,5, AttributeModifier.Operation.ADD_VALUE,null),new AttModifiers(Attributes.MAX_HEALTH,ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"earth_health")
                ,10, AttributeModifier.Operation.ADD_VALUE,null))),
        UNICORN(true,false,List.of(new AttModifiers(Attributes.ATTACK_DAMAGE,ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"uni_damage")
                ,-0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE,null),new AttModifiers(Attributes.MAX_HEALTH,ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"uni_health")
                ,-4, AttributeModifier.Operation.ADD_VALUE,null))),
        PEGASUS(false,true,List.of(new AttModifiers(Attributes.MOVEMENT_SPEED,ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"peg_speed")
                ,0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE,null),new AttModifiers(Attributes.MAX_HEALTH,ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"peg_health")
                ,-2, AttributeModifier.Operation.ADD_VALUE,null)));

        private final boolean canUseMagic;
        private final boolean canFly;
        private final List<AttModifiers> raceModifiers;
        RACES(boolean canUseMagic, boolean canFly, List<AttModifiers> raceModifiers) {
            this.canUseMagic = canUseMagic;
            this.canFly = canFly;
            this.raceModifiers = raceModifiers;
        }
        public boolean canUseMagic() {return canUseMagic;}
        public boolean canFly() {return canFly;}
        public List<AttModifiers> getRaceModifiers() {return raceModifiers;}
    }

    public enum CUTIE_MARKS {
        ERROR(List.of()),
        BLANK(List.of()),
        BUILDING(List.of(new AttModifiers(Attributes.BLOCK_INTERACTION_RANGE,ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"building_range")
                ,3, AttributeModifier.Operation.ADD_VALUE,PonySkills.HIGH_RANGE.id()))),
        MINING(List.of(new AttModifiers(Attributes.BLOCK_BREAK_SPEED,ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"mining_break")
                ,0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE,PonySkills.FAST_MINING.id()))),
        EXPLORING(List.of(new AttModifiers(Attributes.SAFE_FALL_DISTANCE,ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"exploring_fall")
                ,10, AttributeModifier.Operation.ADD_VALUE,PonySkills.FALL_RESIST.id()),new AttModifiers(Attributes.MOVEMENT_SPEED,ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"exploring_fall")
                ,0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE,PonySkills.HIGH_SPEED.id()),new AttModifiers(Attributes.JUMP_STRENGTH,ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,"exploring_fall")
                ,0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE,PonySkills.HIGH_JUMP.id())));

        private final List<AttModifiers> cutieModifiers;
        CUTIE_MARKS(List<AttModifiers> raceModifiers) {
            this.cutieModifiers = raceModifiers;
        }
        public List<AttModifiers> getCutieModifiers() {return cutieModifiers;}
    }
    public record AttModifiers(Holder<Attribute> attribute, ResourceLocation id, double amount, AttributeModifier.Operation operation, @Nullable ResourceLocation skill){}

    public static final StreamCodec<RegistryFriendlyByteBuf, Integer> FRIENDLY_VAR_INT = StreamCodec.of(ByteBufCodecs.VAR_INT::encode, ByteBufCodecs.VAR_INT::decode);

    public static final StreamCodec<RegistryFriendlyByteBuf, RACES> RACE_STREAM_CODEC = FRIENDLY_VAR_INT.map(ordinal -> RACES.values()[ordinal], RACES::ordinal);
    public static final Codec<RACES> RACE_CODEC = Codec.STRING.xmap(RACES::valueOf, RACES::name);
    public static final StreamCodec<RegistryFriendlyByteBuf, CUTIE_MARKS> MARK_STREAM_CODEC = FRIENDLY_VAR_INT.map(ordinal -> CUTIE_MARKS.values()[ordinal], CUTIE_MARKS::ordinal);
    public static final Codec<CUTIE_MARKS> MARK_CODEC = Codec.STRING.xmap(CUTIE_MARKS::valueOf, CUTIE_MARKS::name);

    public static final Codec<PonyData> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    RACE_CODEC.fieldOf("race").forGetter(PonyData::race),
                    MARK_CODEC.fieldOf("cutie_mark").forGetter(PonyData::cutieMark),
                    Codec.BOOL.fieldOf("discoveredCM").forGetter(PonyData::discoveredCM)
            ).apply(instance, PonyData::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, PonyData> STREAM_CODEC =
            StreamCodec.of(
                    (buf, data) -> {
                        RACE_STREAM_CODEC.encode(buf, data.race());
                        MARK_STREAM_CODEC.encode(buf, data.cutieMark());
                        buf.writeBoolean(data.discoveredCM());
                    },
                    buf -> new PonyData(
                            RACE_STREAM_CODEC.decode(buf),
                            MARK_STREAM_CODEC.decode(buf),
                            buf.readBoolean()
                    )
            );
}
