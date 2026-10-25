package net.ona.mlpfrien.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public record LevelData(Set<ResourceLocation> skills, int skillPoints, int skillXp) {
    public static LevelData defaultData() {
        return new LevelData(Set.of(),0,0);
    }

    public boolean hasSkill(ResourceLocation skill) {
        return skills.contains(skill);
    }

    public LevelData {
        skills = Set.copyOf(skills);
    }

    public static final Codec<LevelData> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    ResourceLocation.CODEC.listOf().xmap(set -> Set.copyOf(set), list -> List.copyOf(list)).fieldOf("skills")
                            .forGetter(LevelData::skills),
                    Codec.INT.fieldOf("skill_points").forGetter(LevelData::skillPoints),
                    Codec.INT.fieldOf("skillXp").forGetter(LevelData::skillXp)
            ).apply(instance, LevelData::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, LevelData> STREAM_CODEC =
            StreamCodec.of(
                    (buf, data) -> {
                        buf.writeVarInt(data.skills().size());
                        for (ResourceLocation entry : data.skills()) {
                            buf.writeResourceLocation(entry);
                        }
                        buf.writeVarInt(data.skillPoints());
                        buf.writeVarInt(data.skillXp());
                    },
                    buf -> {
                        int size = buf.readVarInt();
                        Set<ResourceLocation> skills = new HashSet<>();
                        for (int i = 0; i < size; i++) {
                            ResourceLocation id = buf.readResourceLocation();
                            skills.add(id);
                        }
                        int skillPoints = buf.readVarInt();
                        int level = buf.readVarInt();

                        return new LevelData(skills, skillPoints, level);
                    }
            );
}
