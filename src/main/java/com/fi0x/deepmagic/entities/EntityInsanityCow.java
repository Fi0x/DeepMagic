package com.fi0x.deepmagic.entities;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityInsanityCow extends EntityCow
{
    public EntityInsanityCow(World worldIn)
    {
        super(worldIn);
        setSize(1.0F, 1.4F);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.5D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.25D, Items.WHEAT, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.27);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32);
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