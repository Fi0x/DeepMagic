package com.fi0x.deepmagic.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class EntitySpellExplosion extends EntityFireball
{
    public EntitySpellExplosion(World world)
    {
        super(world);
        this.setSize(0.5F, 0.5F);
    }
    public EntitySpellExplosion(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ)
    {
        super(worldIn, shooter, accelX, accelY, accelZ);
        this.setSize(0.5F, 0.5F);
    }
    @SideOnly(Side.CLIENT)
    public EntitySpellExplosion(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ)
    {
        super(worldIn, x, y, z, accelX, accelY, accelZ);
        this.setSize(0.5F, 0.5F);
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource source, float amount)
    {
        return false;
    }
    @Override
    protected void onImpact(@Nonnull RayTraceResult result)
    {
        if (!this.world.isRemote)
        {
            if (result.entityHit != null)
            {
                if (this.shootingEntity == null) result.entityHit.attackEntityFrom(DamageSource.MAGIC, 2.0F);
                world.newExplosion(shootingEntity, posX, posY, posZ, 1, true, false);
            }
            this.setDead();
        }
    }
}