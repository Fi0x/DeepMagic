package com.fi0x.deepmagic.entities.mobs;

import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.util.handlers.LootTableHandler;
import com.fi0x.deepmagic.util.handlers.SoundsHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityInsanityCow extends EntityCow
{
    private static final DataParameter<Boolean> SADDLED = EntityDataManager.createKey(EntityInsanityCow.class, DataSerializers.BOOLEAN);

    public EntityInsanityCow(World worldIn)
    {
        super(worldIn);
        setSize(1.0F, 1.4F);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(SADDLED, Boolean.FALSE);
    }
    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.5D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 2D, Items.CARROT_ON_A_STICK, false));
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
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ConfigHandler.healthInsanityCow);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.27);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32);
    }
    @Override
    public EntityCow createChild(@Nonnull EntityAgeable ageable)
    {
        return new EntityInsanityCow(world);
    }
    @Override
    public boolean processInteract(@Nonnull EntityPlayer player, @Nonnull EnumHand hand)
    {
        if(!super.processInteract(player, hand))
        {
            ItemStack itemstack = player.getHeldItem(hand);

            if(itemstack.getItem() == Items.NAME_TAG)
            {
                itemstack.interactWithEntity(player, this, hand);
                return true;
            } else if(this.getSaddled() && !this.isBeingRidden())
            {
                if(!this.world.isRemote) player.startRiding(this);
                return true;
            } else if(itemstack.getItem() == Items.SADDLE)
            {
                setSaddled(true);
                world.playSound(player, posX, posY, posZ, SoundEvents.ENTITY_PIG_SADDLE, SoundCategory.NEUTRAL, 0.5F, 1.0F);
                itemstack.shrink(1);
                return true;
            } else return false;
        } else return true;
    }
    @Nullable
    @Override
    public Entity getControllingPassenger()
    {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }
    @Override
    protected boolean canBeRidden(@Nonnull Entity entityIn)
    {
        return true;
    }
    @Override
    public boolean canBeSteered()
    {
        Entity entity = this.getControllingPassenger();

        if(!(entity instanceof EntityPlayer))
        {
            return false;
        } else
        {
            EntityPlayer entityplayer = (EntityPlayer) entity;
            return entityplayer.getHeldItemMainhand().getItem() == Items.CARROT_ON_A_STICK || entityplayer.getHeldItemOffhand().getItem() == Items.CARROT_ON_A_STICK;
        }
    }
    public boolean getSaddled()
    {
        return this.dataManager.get(SADDLED);
    }
    public void setSaddled(boolean saddled)
    {
        if(saddled)
        {
            this.dataManager.set(SADDLED, Boolean.TRUE);
        } else
        {
            this.dataManager.set(SADDLED, Boolean.FALSE);
        }
    }
    @Override
    public void travel(float strafe, float vertical, float forward)
    {
        Entity entity = this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);

        if(this.isBeingRidden() && this.canBeSteered())
        {
            assert entity != null;
            this.rotationYaw = entity.rotationYaw;
            this.prevRotationYaw = this.rotationYaw;
            this.rotationPitch = entity.rotationPitch * 0.5F;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.renderYawOffset = this.rotationYaw;
            this.rotationYawHead = this.rotationYaw;
            this.stepHeight = 1.0F;
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;

            if(this.canPassengerSteer())
            {
                float f = (float) this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * 0.225F;

                this.setAIMoveSpeed(f);
                super.travel(0.0F, 0.0F, 1.0F);
            } else
            {
                this.motionX = 0.0D;
                this.motionY = 0.0D;
                this.motionZ = 0.0D;
            }

            this.prevLimbSwingAmount = this.limbSwingAmount;
            double d1 = this.posX - this.prevPosX;
            double d0 = this.posZ - this.prevPosZ;
            float f1 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;

            if(f1 > 1.0F)
            {
                f1 = 1.0F;
            }

            this.limbSwingAmount += (f1 - this.limbSwingAmount) * 0.4F;
            this.limbSwing += this.limbSwingAmount;
        } else
        {
            this.stepHeight = 0.5F;
            this.jumpMovementFactor = 0.02F;
            super.travel(strafe, vertical, forward);
        }
    }
    @Override
    public void writeEntityToNBT(@Nonnull NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Saddle", this.getSaddled());
    }
    @Override
    public void readEntityFromNBT(@Nonnull NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setSaddled(compound.getBoolean("Saddle"));
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundsHandler.ENTITY_INSANITY_COW_AMBIENT;
    }
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource source)
    {
        return SoundsHandler.ENTITY_INSANITY_COW_HURT;
    }
    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundsHandler.ENTITY_INSANITY_COW_DEATH;
    }
    @Override
    protected void playStepSound(@Nonnull BlockPos pos, @Nonnull Block blockIn)
    {
        this.playSound(SoundsHandler.ENTITY_INSANITY_COW_STEP, 1, 1);
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
        return LootTableHandler.INSANITY_COW;
    }
    @Override
    public void onDeath(@Nonnull DamageSource cause)
    {
        super.onDeath(cause);
        if(!this.world.isRemote)
        {
            if(this.getSaddled())
            {
                this.dropItem(Items.SADDLE, 1);
            }
        }
    }
}