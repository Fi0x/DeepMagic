package com.fi0x.deepmagic.entities;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class EntityInsanityCow extends EntityCow
{
    public EntityInsanityCow(World worldIn)
    {
        super(worldIn);
    }

    @Override
    public EntityCow createChild(@Nonnull EntityAgeable ageable)
    {
        return new EntityInsanityCow(world);
    }
    @Override
    protected SoundEvent getAmbientSound()
    {
        return super.getAmbientSound();
    }
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource source)
    {
        return super.getHurtSound(source);
    }
    @Override
    protected SoundEvent getDeathSound()
    {
        return super.getDeathSound();
    }
}