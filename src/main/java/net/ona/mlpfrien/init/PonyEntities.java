package net.ona.mlpfrien.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.magic.UnicornSpellProjectile;

public class PonyEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(
                    BuiltInRegistries.ENTITY_TYPE,
                    MyLittlePonyCraftingFriends.MODID
            );

    public static final DeferredHolder<EntityType<?>, EntityType<UnicornSpellProjectile>>
            UNICORN_SPELL_PROJECTILE =
            ENTITY_TYPES.register("unicorn_spell_projectile", () -> EntityType.Builder
                            .<UnicornSpellProjectile>of(UnicornSpellProjectile::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F).clientTrackingRange(64).updateInterval(1).build("unicorn_spell_projectile"));
}
