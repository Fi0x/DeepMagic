package com.fi0x.deepmagic.entities;

import com.fi0x.deepmagic.util.handlers.LootTableHandler;
import com.fi0x.deepmagic.util.handlers.SoundsHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityDwarf extends EntityCreature
{
    public EntityDwarf(World worldIn)
    {
        super(worldIn);
        this.setSize(0.6F, 1F);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundsHandler.ENTITY_DWARF_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSourceIn)
    {
        return SoundsHandler.ENTITY_DWARF_HURT;
    }
    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundsHandler.ENTITY_DWARF_DEATH;
    }
    @Override
    protected void playStepSound(@Nonnull BlockPos pos, @Nonnull Block blockIn)
    {
        this.playSound(SoundsHandler.ENTITY_DWARF_STEP, 1F, 1F);
    }
    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return LootTableHandler.DWARF;
    }
    @Override
    public float getEyeHeight()
    {
        return 0.8F;
    }
}