package net.ona.mlpfrien.magic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.List;

public record UnicornSpell(String name, List<SpellComponent> componentList, float cost) {
    public static UnicornSpell registerSpell(String name, List<SpellComponent> components) {
        return new UnicornSpell(name,components,spellCost(components));
    }

    public static float spellCost(List<SpellComponent> components) {
        float cost = 1;
        for (SpellComponent component : components) {
            switch (component) {
                case SELF, WAIT -> cost ++;
                case PROJECTILE, TOUCH, DAMAGE, HEAL, FIRE -> cost+=2;
                case TELEPORT -> cost+=3;
                case EXPLODE, AMPLIFY -> cost+=4;
            }
            cost*=1.1f;
        }
        return cost;
    }

    public static final Codec<UnicornSpell> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                            Codec.STRING.fieldOf("name").forGetter(UnicornSpell::name),
                            SpellComponent.CODEC.listOf().fieldOf("components").forGetter(UnicornSpell::componentList),
                            Codec.FLOAT.fieldOf("mana_cost").forGetter(UnicornSpell::cost)).apply(instance, UnicornSpell::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, UnicornSpell> STREAM_CODEC =
            StreamCodec.of(
                    (buf, spell) -> {
                        buf.writeUtf(spell.name());
                        buf.writeVarInt(spell.componentList.size());
                        for (SpellComponent component : spell.componentList()) {
                            SpellComponent.STREAM_CODEC.encode(buf, component);
                        }
                        buf.writeFloat(spell.cost);
                    },
                    buf -> {
                        String name = buf.readUtf();
                        int componentCount = buf.readVarInt();
                        List<SpellComponent> components = new ArrayList<>(componentCount);
                        for (int i = 0; i < componentCount; i++) {components.add(SpellComponent.STREAM_CODEC.decode(buf));}
                        float cost = buf.readFloat();

                        return new UnicornSpell(name, List.copyOf(components), cost);
                    }
            );
}
