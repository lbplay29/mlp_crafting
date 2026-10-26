package net.ona.mlpfrien.data.skill;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.ona.mlpfrien.data.LevelData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record SkillStateData(Set<ResourceLocation> activeModes) {
    public static SkillStateData defaultData() {
        return new SkillStateData(Set.of());
    }

    public boolean isActive(PonySkills.PonySkill skill) {
        return activeModes.contains(skill.id());
    }

    public SkillStateData {
        activeModes = Set.copyOf(activeModes);
    }

    public static final Codec<SkillStateData> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    ResourceLocation.CODEC.listOf().xmap(Set::copyOf, List::copyOf).fieldOf("skills")
                            .forGetter(SkillStateData::activeModes)
            ).apply(instance, SkillStateData::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SkillStateData> STREAM_CODEC =
            StreamCodec.of(
                    (buf, data) -> {
                        buf.writeVarInt(data.activeModes.size());
                        for (ResourceLocation entry : data.activeModes) {
                            buf.writeResourceLocation(entry);
                        }
                    },
                    buf -> {
                        int size = buf.readVarInt();
                        Set<ResourceLocation> activeModes = new HashSet<>();
                        for (int i = 0; i < size; i++) {
                            ResourceLocation id = buf.readResourceLocation();
                            activeModes.add(id);
                        }
                        return new SkillStateData(activeModes);
                    }
            );
}
