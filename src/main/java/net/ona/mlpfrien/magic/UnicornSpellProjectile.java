package net.ona.mlpfrien.magic;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.init.PonyEntities;

import java.util.List;

public class UnicornSpellProjectile extends ThrowableProjectile {
    private List<SpellComponent> components = List.of();
    private boolean wasTouchingUnloadedChunk = false;

    public UnicornSpellProjectile(EntityType<? extends UnicornSpellProjectile> type, Level level) {
        super(type, level);
    }

    public void setComponents(List<SpellComponent> components) {
        this.components = List.copyOf(components);
    }

    @Override
    protected double getDefaultGravity() {
        return 0.0D;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        if (level().isClientSide()) {return;}

        if (!(getOwner() instanceof ServerPlayer caster)) {
            discard();
            return;
        }
        SpellContext context = new SpellContext(caster);
        context.setTarget(result.getEntity());

        SpellCaster.executeComponents(context, components);
        discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

        if (level().isClientSide()) {discard();}

        if (!(getOwner() instanceof ServerPlayer caster)) {
            discard();
            return;
        }
        SpellContext context = new SpellContext(caster);
        context.setBlockTarget(result.getLocation());

        SpellCaster.executeComponents(context, components);
        discard();
    }

    @Override
    public void tick() {
        super.tick();
        boolean touching = this.touchingUnloadedChunk();

        if (level().isClientSide()) {
            for (int i=0;i<10;i++) {
                level().addParticle(ParticleTypes.END_ROD, getX()-(random.nextDouble()/3), getY()-(random.nextDouble()/3), getZ()-(random.nextDouble()/3),
                        (random.nextDouble() - 0.5) * 0.03,
                        (random.nextDouble() - 0.5) * 0.03,
                        (random.nextDouble() - 0.5) * 0.03);
            }
        }
        if (touching && !wasTouchingUnloadedChunk) {
            if (!(getOwner() instanceof ServerPlayer caster)) {
                discard();
                return;
            }
            SpellContext context = new SpellContext(caster);
            context.setBlockTarget(position());

            SpellCaster.executeComponents(context, components);
            discard();
        }
        wasTouchingUnloadedChunk = touching;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }
}
