package com.fi0x.deepmagic.entities;

import com.fi0x.deepmagic.util.IMagicCreature;
import com.fi0x.deepmagic.util.handlers.LootTableHandler;
import com.fi0x.deepmagic.util.handlers.SoundsHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityCockroach extends EntityMob implements IMagicCreature
{
    public EntityCockroach(World worldIn)
    {
        super(worldIn);
        this.setSize(0.8F, 0.2F);
    }

    @Override
    protected void initEntityAI()
    {
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
        return SoundsHandler.ENTITY_COCKROACH_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSourceIn)
    {
        return SoundsHandler.ENTITY_COCKROACH_HURT;
    }
    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundsHandler.ENTITY_COCKROACH_DEATH;
    }
    @Override
    protected void playStepSound(@Nonnull BlockPos pos, @Nonnull Block blockIn)
    {
        this.playSound(SoundsHandler.ENTITY_COCKROACH_STEP, 1F, 1F);
    }
    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return LootTableHandler.COCKROACH;
    }
    @Override
    public float getEyeHeight()
    {
        return 0.1F;
    }
}