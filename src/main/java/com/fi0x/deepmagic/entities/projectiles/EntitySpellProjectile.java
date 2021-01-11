package com.fi0x.deepmagic.entities.projectiles;

import com.fi0x.deepmagic.particlesystem.ParticleEnum;
import com.fi0x.deepmagic.particlesystem.ParticleSpawner;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class EntitySpellProjectile extends EntityThrowable
{
    public double existingSeconds;

    public EntitySpellProjectile(World worldIn)
    {
        super(worldIn);
        setNoGravity(true);
    }
    public EntitySpellProjectile(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
        setNoGravity(true);
    }
    public EntitySpellProjectile(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
        setNoGravity(true);
    }
    @Override
    public void onUpdate()
    {
        super.onUpdate();
        if(ticksExisted > 20 * existingSeconds) setDead();
        double x = posX + Math.random() - 0.5;
        double y = posY + Math.random() - 0.5;
        double z = posZ + Math.random() - 0.5;
        ParticleSpawner.spawnParticle(ParticleEnum.SPELL_PROJECTILE, x, y, z, 0, 0, 0, Math.random() + 0.5, false, 64);
    }
    @Override
    protected void onImpact(@Nonnull RayTraceResult result)
    {
        //TODO: Execute spell
        setDead();
    }

    @Override
    protected float getGravityVelocity()
    {
        if(hasNoGravity()) return 0;
        else return super.getGravityVelocity();
    }
}