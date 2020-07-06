package com.fi0x.deepmagic.entities;

import com.fi0x.deepmagic.entities.ai.EntityAIMining;
import com.fi0x.deepmagic.util.handlers.LootTableHandler;
import com.fi0x.deepmagic.util.handlers.SoundsHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityDwarf extends EntityCreature
{
    public boolean isMining;

    public EntityDwarf(World worldIn)
    {
        super(worldIn);
        isMining = false;
        this.setSize(1F, 1.5F);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1, false));
        this.tasks.addTask(2, new EntityAIMining(this));
        this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, 1));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, Entity.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));

        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_SPEED);

        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
        getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(30.0D);
        getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(20);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).setBaseValue(8);
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
        return 1.2F;
    }
}