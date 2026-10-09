package net.ona.mlpfrien.magic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.List;

public record UnicornData(List<UnicornSpell> spells,List<UnicornSpell> equippedSpells,int currentSlot, int spellSlots, float mana, float max_mana, int level) {
    public static UnicornData defaultData() {
        return new UnicornData(List.of(UnicornSpells.damage_bullet),List.of(UnicornSpells.damage_bullet),0,2,50,50,1);
    }

    public static final Codec<UnicornData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                            UnicornSpell.CODEC.listOf().fieldOf("spells").forGetter(UnicornData::spells),
                            UnicornSpell.CODEC.listOf().fieldOf("equipped_spells").forGetter(UnicornData::equippedSpells),
                            Codec.INT.fieldOf("currentSlot").forGetter(UnicornData::currentSlot),
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
                        for (UnicornSpell spellE : data.equippedSpells()) {
                            UnicornSpell.STREAM_CODEC.encode(buf, spellE);
                        }
                        buf.writeVarInt(data.currentSlot);
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
                        int spellECount = buf.readVarInt();
                        List<UnicornSpell> spellsE = new ArrayList<>(spellECount);
                        for (int i = 0; i < spellECount; i++) {
                            spellsE.add(UnicornSpell.STREAM_CODEC.decode(buf));
                        }
                        int currentSlot = buf.readVarInt();
                        int spellSlots = buf.readVarInt();
                        float mana = buf.readFloat();
                        float max_mana = buf.readFloat();
                        int level = buf.readVarInt();

                        return new UnicornData(List.copyOf(spells),List.copyOf(spellsE),currentSlot,spellSlots,mana,max_mana,level);
                    }
            );
}