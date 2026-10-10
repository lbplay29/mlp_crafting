package net.ona.mlpfrien.magic;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.init.PonyEntities;

import java.util.List;

public class SpellCaster {
    public static void castSpell(ServerPlayer caster, UnicornSpell spell) {
        SpellContext context = new SpellContext(caster);
        useMana(caster,spell.cost());
        List<SpellComponent> components = spell.componentList();

        for (int i = 0; i < components.size(); i++) {

            SpellComponent component = components.get(i);

            if (component == SpellComponent.PROJECTILE) {
                List<SpellComponent> remaining = components.subList(i + 1, components.size());
                spawnProjectile(context, remaining);
                return;
            }

            component.apply(context);
        }
    }

    private static void spawnProjectile(SpellContext context, List<SpellComponent> components) {
        UnicornSpellProjectile projectile = new UnicornSpellProjectile(PonyEntities.UNICORN_SPELL_PROJECTILE.get(), context.getCaster().level());

        projectile.setOwner(context.getCaster());
        projectile.setComponents(components);

        projectile.setPos(context.getCaster().getX(), context.getCaster().getEyeY(), context.getCaster().getZ());

        projectile.shootFromRotation(context.getCaster(), context.getCaster().getXRot(), context.getCaster().getYRot(), 0.0F, 1.5F, 0.0F);

        context.getCaster().level().addFreshEntity(projectile);
    }

    public static void executeComponents(SpellContext context, List<SpellComponent> components) {
        for (int i = 0; i < components.size(); i++) {

            SpellComponent component = components.get(i);

            if (component == SpellComponent.WAIT) {
                List<SpellComponent> remaining = components.subList(i + 1, components.size());
                DelayedSpellManager.schedule(20,context,remaining);
                return;
            }

            component.apply(context);
        }
    }

    public static void useMana(Player player, float subtraction) {
        UnicornData data = player.getData(PonyAttachments.UNICORN_DATA);
        player.setData(PonyAttachments.UNICORN_DATA, new UnicornData(data.spells(),data.equippedSpells(),
                 data.spellSlots(), data.mana()-(subtraction), data.max_mana(), data.level()));
    }
}
