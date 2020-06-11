package com.fi0x.deepmagic.entities;

import com.fi0x.deepmagic.util.handlers.LootTableHandler;
import com.fi0x.deepmagic.util.handlers.SoundsHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityNetherWorm extends EntityMob
{
    public EntityNetherWorm(World worldIn)
    {
        super(worldIn);
        setSize(1F, 1F);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAILeapAtTarget(this, 1F));
        this.tasks.addTask(0, new EntityAIAttackMelee(this, 2, false));
        this.tasks.addTask(0, new EntityAIWander(this, 1));
        this.tasks.addTask(0, new EntityAILookIdle(this));
    }
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
        getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(100.0D);
    }
    @Override
    public float getEyeHeight()
    {
        return 0.9F;
    }
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundsHandler.ENTITY_NETHER_WORM_AMBIENT;
    }
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource source)
    {
        return SoundsHandler.ENTITY_NETHER_WORM_HURT;
    }
    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundsHandler.ENTITY_NETHER_WORM_DEATH;
    }

    @Override
    protected void playStepSound(@Nonnull BlockPos pos, @Nonnull Block blockIn)
    {
        this.playSound(SoundsHandler.ENTITY_NETHER_WORM_STEP, 1F, 1F);
    }

    @Override
    protected float getSoundVolume()
    {
        return 1F;
    }
    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return super.getLootTable();
    }
}