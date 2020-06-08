package com.fi0x.deepmagic.entities;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntitySpikySlime extends EntitySlime
{
    public EntitySpikySlime(World worldIn)
    {
        super(worldIn);
        setSize(1.0F, 1.4F);
    }

    @Override
    protected void initEntityAI()
    {
        super.initEntityAI();
    }
    @Override
    protected void alterSquishAmount()
    {
        this.squishAmount *= 0.4F;
    }
    @Override
    protected int getJumpDelay()
    {
        return this.rand.nextInt(60);
    }
    @Override
    protected boolean canDamagePlayer()
    {
        return true;
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
    protected int getAttackStrength()
    {
        return 2*super.getAttackStrength();
    }
    @Override
    public void setDead()
    {
        int i = this.getSlimeSize();

        if (!this.world.isRemote && i > 1 && this.getHealth() <= 0.0F)
        {
            int j = 2 + this.rand.nextInt(3);

            for (int k = 0; k < j; ++k)
            {
                float f = ((float)(k % 2) - 0.5F) * (float)i / 4.0F;
                float f1 = ((float)(k / 2) - 0.5F) * (float)i / 4.0F;
                EntitySpikySlime entitySpikySlime = this.createInstance();

                if (this.hasCustomName())
                {
                    entitySpikySlime.setCustomNameTag(this.getCustomNameTag());
                }

                if (this.isNoDespawnRequired())
                {
                    entitySpikySlime.enablePersistence();
                }

                entitySpikySlime.setSlimeSize(i / 2, true);
                entitySpikySlime.setLocationAndAngles(this.posX + (double)f, this.posY + 0.5D, this.posZ + (double)f1, this.rand.nextFloat() * 360.0F, 0.0F);
                this.world.spawnEntity(entitySpikySlime);
            }
        }
        this.isDead = true;
    }
    @Override
    protected void setSlimeSize(int size, boolean resetHealth)
    {
        super.setSlimeSize(size, resetHealth);
    }
    @Nonnull
    @Override
    protected EntitySpikySlime createInstance()
    {
        return new EntitySpikySlime(this.world);
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
    @Nonnull
    @Override
    protected SoundEvent getSquishSound()
    {
        return super.getSquishSound();
    }
    @Override
    protected float getSoundVolume()
    {
        return 0.5F;
    }
    @Override
    protected Item getDropItem()
    {
        return Items.SLIME_BALL;
    }
    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return LootTableList.ENTITIES_SLIME;
    }
}