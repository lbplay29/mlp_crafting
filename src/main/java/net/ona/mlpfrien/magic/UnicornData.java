package net.ona.mlpfrien.magic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record UnicornData(List<UnicornSpell> spells, List<Optional<UnicornSpell>> equippedSpells, int spellSlots, float mana, float max_mana, int level) {
    public static UnicornData defaultData() {
        return new UnicornData(List.of(UnicornSpells.damage_bullet),List.of(Optional.of(UnicornSpells.damage_bullet)),2,50,50,1);
    }
    public UnicornData {
        spells = List.copyOf(spells);
        List<Optional<UnicornSpell>> normalizedSlots = new ArrayList<>(spellSlots);
        for (int i = 0; i < spellSlots; i++) {
            if (i < equippedSpells.size()) {
                normalizedSlots.add(equippedSpells.get(i));
            }
            else {
                normalizedSlots.add(Optional.empty());
            }
        }
        equippedSpells = List.copyOf(normalizedSlots);
    }

    private record SpellSlot(Optional<UnicornSpell> spell) {
        public static final Codec<SpellSlot> CODEC =
                RecordCodecBuilder.create(instance -> instance.group(UnicornSpell.CODEC.optionalFieldOf("spell")
                        .forGetter(SpellSlot::spell)).apply(instance, SpellSlot::new));
    }
    private static final Codec<Optional<UnicornSpell>> SPELL_SLOT_CODEC = SpellSlot.CODEC.xmap(SpellSlot::spell, SpellSlot::new);

    public static final Codec<UnicornData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                            UnicornSpell.CODEC.listOf().fieldOf("spells").forGetter(UnicornData::spells),
                            SPELL_SLOT_CODEC.listOf().fieldOf("equipped_spells").forGetter(UnicornData::equippedSpells),
                            Codec.INT.fieldOf("spellSlots").forGetter(UnicornData::spellSlots),
                            Codec.FLOAT.fieldOf("mana").forGetter(UnicornData::mana),
                            Codec.FLOAT.fieldOf("max_mana").forGetter(UnicornData::max_mana),
                            Codec.INT.fieldOf("level").forGetter(UnicornData::level)).apply(instance, UnicornData::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, UnicornData> STREAM_CODEC =
            StreamCodec.of(
                    (buf, data) -> {
                        buf.writeVarInt(data.spells().size());
                        for (UnicornSpell spell : data.spells()) {
                            UnicornSpell.STREAM_CODEC.encode(buf, spell);
                        }
                        buf.writeVarInt(data.equippedSpells().size());
                        for (Optional<UnicornSpell> spellE : data.equippedSpells()) {
                            boolean occupied = spellE.isPresent();
                            buf.writeBoolean(occupied);

                            if (occupied) {
                                UnicornSpell.STREAM_CODEC.encode(buf, spellE.get());
                            }
                        }
                        buf.writeVarInt(data.spellSlots);
                        buf.writeFloat(data.mana);
                        buf.writeFloat(data.max_mana);
                        buf.writeVarInt(data.level);
                    },

                    buf -> {
                        int spellCount = buf.readVarInt();
                        List<UnicornSpell> spells = new ArrayList<>(spellCount);
                        for (int i = 0; i < spellCount; i++) {
                            spells.add(UnicornSpell.STREAM_CODEC.decode(buf));
                        }
                        int equippedCount = buf.readVarInt();
                        List<Optional<UnicornSpell>> equipped = new ArrayList<>(equippedCount);
                        for (int i = 0; i < equippedCount; i++) {
                            boolean occupied = buf.readBoolean();
                            if (occupied) {
                                UnicornSpell spell = UnicornSpell.STREAM_CODEC.decode(buf);
                                equipped.add(Optional.of(spell));
                            }
                            else {
                                equipped.add(Optional.empty());
                            }
                        }
                        int spellSlots = buf.readVarInt();
                        float mana = buf.readFloat();
                        float max_mana = buf.readFloat();
                        int level = buf.readVarInt();

                        return new UnicornData(List.copyOf(spells),List.copyOf(equipped),spellSlots,mana,max_mana,level);
                    }
            );
}