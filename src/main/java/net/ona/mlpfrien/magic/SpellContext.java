package net.ona.mlpfrien.magic;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class SpellContext {

    private final ServerPlayer caster;
    private Entity target;

    private float power = 1.0F;
    private int duration = 20;
    private Vec3 blockTarget = new Vec3(0,0,0);

    public SpellContext(ServerPlayer caster) {
        this.caster = caster;
    }

    public float getPower() {
        return power;
    }
    public void setPower(float power) {
        this.power = power;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public ServerPlayer getCaster() {
        return caster;
    }
    public @Nullable Entity getTarget() {
        return target;
    }
    public void setTarget(Entity target) {this.target = target;}
    public Vec3 getBlockTarget() {return blockTarget;}
    public void setBlockTarget(Vec3 blockTarget) {this.blockTarget = blockTarget;}
}