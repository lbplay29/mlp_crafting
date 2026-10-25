package net.ona.mlpfrien.data;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;

import java.util.function.Supplier;

public class PonyAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MyLittlePonyCraftingFriends.MODID);

    public static final Supplier<AttachmentType<PonyData>> PONY_DATA = ATTACHMENT_TYPES.register("pony_data",()-> AttachmentType.builder(PonyData::defaultData)
            .serialize(PonyData.CODEC)
            .sync(PonyData.STREAM_CODEC)
            .copyOnDeath()
            .build());
    public static final Supplier<AttachmentType<UnicornData>> UNICORN_DATA = ATTACHMENT_TYPES.register("unicorn_data",()-> AttachmentType.builder(UnicornData::defaultData)
            .serialize(UnicornData.CODEC)
            .sync(UnicornData.STREAM_CODEC)
            .copyOnDeath()
            .build());
    public static final Supplier<AttachmentType<AppearanceData>> APPEARANCE_DATA = ATTACHMENT_TYPES.register("appearance_data",()-> AttachmentType.builder(AppearanceData::defaultData)
            .serialize(AppearanceData.CODEC)
            .sync(AppearanceData.STREAM_CODEC)
            .copyOnDeath()
            .build());
    public static final Supplier<AttachmentType<LevelData>> LEVEL_DATA = ATTACHMENT_TYPES.register("level_data",()-> AttachmentType.builder(LevelData::defaultData)
            .serialize(LevelData.CODEC)
            .sync(LevelData.STREAM_CODEC)
            .copyOnDeath()
            .build());
}
