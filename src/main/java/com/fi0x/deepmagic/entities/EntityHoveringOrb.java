package com.fi0x.deepmagic.entities;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityHoveringOrb extends EntityMob
{
    public EntityHoveringOrb(World worldIn)
    {
        super(worldIn);
        setSize(1F, 2F);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1, false));
        this.tasks.addTask(1, new EntityAILeapAtTarget(this, 0.5F));
        this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(6, new EntityAILookIdle(this));
    }
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.27);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(25.0D);
        getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
    }
    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }
    @Override
    public float getEyeHeight()
    {
        return 1.5F;
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
    @Override
    protected float getSoundVolume()
    {
        return 0.5F;
    }
    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return super.getLootTable();
    }
}