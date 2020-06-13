package com.fi0x.deepmagic.entities;

import com.fi0x.deepmagic.util.handlers.LootTableHandler;
import com.fi0x.deepmagic.util.handlers.SoundsHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityRockTroll extends EntityCreature
{
    public EntityRockTroll(World worldIn)
    {
        super(worldIn);
        setSize(2F, 3F);
        this.isImmuneToFire = true;
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1, false));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(3, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(4, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.27);
    }
    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }
    @Override
    public float getEyeHeight()
    {
        return 0.5F;
    }
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundsHandler.ENTITY_ROCK_TROLL_AMBIENT;
    }
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource source)
    {
        return SoundsHandler.ENTITY_ROCK_TROLL_HURT;
    }
    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundsHandler.ENTITY_ROCK_TROLL_DEATH;
    }
    @Override
    protected void playStepSound(@Nonnull BlockPos pos, @Nonnull Block blockIn)
    {
        this.playSound(SoundsHandler.ENTITY_ROCK_TROLL_STEP, 1F, 1F);
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
        return LootTableHandler.HOVERING_ORB;
    }
}