package net.ona.mlpfrien.magic;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public enum SpellComponent {

    DAMAGE(SpellComponentType.EFFECT) {
        @Override
        public void apply(SpellContext context) {
            if (context.getTarget() instanceof LivingEntity target) {
                target.hurt(context.getCaster().damageSources().magic(), 4.0F*context.getPower());
            } else {
                BlockState block = context.getCaster().level().getBlockState(BlockPos.containing(context.getBlockTarget()));
                if (block.is(BlockTags.SCULK_REPLACEABLE)) {
                    context.getCaster().level().removeBlock(BlockPos.containing(context.getBlockTarget()),false);
                }
            }
        }
    },

    TELEPORT(SpellComponentType.EFFECT) {
        @Override
        public void apply(SpellContext context) {
            if (context.getTarget() != null) {
                context.getCaster().teleportTo(context.getTarget().position().x,context.getTarget().position().y,context.getTarget().position().z);
            } else {
                Vec3 pos = Vec3.atLowerCornerOf(BlockPos.containing(context.getBlockTarget()).above());
                context.getCaster().teleportTo(pos.x,pos.y,pos.z);
            }
        }
    },

    AMPLIFY(SpellComponentType.MODIFIER) {
        @Override
        public void apply(SpellContext context) {
            context.setPower(context.getPower()*1.25f);
        }
    },

    WAIT(SpellComponentType.MODIFIER) {
        @Override
        public void apply(SpellContext context) {
            //spellcaster handles this one.
        }
    },

    FIRE(SpellComponentType.EFFECT) {
        @Override
        public void apply(SpellContext context) {
            if (context.getTarget() != null) {
                context.getTarget().igniteForSeconds(5*context.getPower());
            } else {
                context.getCaster().level().setBlockAndUpdate(BlockPos.containing(context.getBlockTarget()).above(), Blocks.FIRE.defaultBlockState());
            }
        }
    },

    SELF(SpellComponentType.FORM) {
        @Override
        public void apply(SpellContext context) {
            context.setTarget(context.getCaster());
        }
    },

    PROJECTILE(SpellComponentType.FORM) {
        @Override
        public void apply(SpellContext context) {
            //spellcaster handles this one.
        }
    },

    TOUCH(SpellComponentType.FORM) {
        @Override
        public void apply(SpellContext context) {
            HitResult hit = context.getCaster().pick(10*context.getPower(), 1.0F, false);

            if (hit.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHit = (BlockHitResult) hit;

                Vec3 pos = blockHit.getLocation();
                context.setBlockTarget(pos);
            } else if (hit.getType() == HitResult.Type.ENTITY) {
                EntityHitResult result = (EntityHitResult) hit;

                context.setTarget(result.getEntity());
            }
        }
    },

    EXPLODE(SpellComponentType.EFFECT) {
        @Override
        public void apply(SpellContext context) {
            if (context.getTarget()!=null) {
                context.getCaster().level().explode(
                        context.getCaster(),
                        context.getTarget().getX(),
                        context.getTarget().getY(),
                        context.getTarget().getZ(),
                        3.0F*context.getPower(), Level.ExplosionInteraction.MOB);
            } else {
                context.getCaster().level().explode(
                        context.getCaster(),
                        context.getBlockTarget().x,
                        context.getBlockTarget().y,
                        context.getBlockTarget().z,
                        3.0F*context.getPower(), Level.ExplosionInteraction.MOB);
            }
        }
    },

    HEAL(SpellComponentType.EFFECT) {
        @Override
        public void apply(SpellContext context) {
            if (context.getTarget() instanceof LivingEntity target) {
                target.heal(4.0F);
            }
        }
    };

    private final SpellComponentType type;

    SpellComponent(SpellComponentType type) {
        this.type = type;
    }

    public SpellComponentType type() {
        return type;
    }

    public abstract void apply(SpellContext context);

    public static final Codec<SpellComponent> CODEC = Codec.STRING.xmap(SpellComponent::valueOf, SpellComponent::name);
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellComponent> STREAM_CODEC =
            StreamCodec.of(
                    (buf, component) -> buf.writeUtf(component.name()),
                    buf -> SpellComponent.valueOf(buf.readUtf())
            );
}
