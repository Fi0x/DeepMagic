package com.fi0x.deepmagic.entities.mobs;

import com.fi0x.deepmagic.entities.ai.EntityAIDefence;
import com.fi0x.deepmagic.network.PacketReturnMobAnimation;
import com.fi0x.deepmagic.util.CustomNameGenerator;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.util.handlers.LootTableHandler;
import com.fi0x.deepmagic.util.handlers.PacketHandler;
import com.fi0x.deepmagic.util.handlers.SoundsHandler;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityRockTroll extends EntityCreature
{
    public int defenceTime;
    public int attackTimer;

    public EntityRockTroll(World worldIn)
    {
        super(worldIn);
        setSize(2F, 2.5F);
        this.isImmuneToFire = true;
        defenceTime = 0;
        attackTimer = 0;
        this.setCustomNameTag(CustomNameGenerator.getRandomTrollName());
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAIDefence(this));
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
        getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ConfigHandler.healthRockTroll);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.27);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ConfigHandler.damageRockTroll);
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
        return LootTableHandler.ROCK_TROLL;
    }

    @Override
    public boolean isEntityInvulnerable(@Nonnull DamageSource source)
    {
        return defenceTime > 0 && !source.isCreativePlayer();
    }
    @Override
    public void onUpdate()
    {
        super.onUpdate();
        PacketHandler.INSTANCE.sendToAllAround(new PacketReturnMobAnimation(this.getEntityId(), attackTimer, defenceTime), new NetworkRegistry.TargetPoint(world.provider.getDimension(), posX, posY, posZ, 64));
    }

    @Override
    public boolean attackEntityAsMob(@Nonnull Entity entityIn)
    {
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        int i = 0;

        if (entityIn instanceof EntityLivingBase)
        {
            f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase)entityIn).getCreatureAttribute());
            i += EnchantmentHelper.getKnockbackModifier(this);
        }

        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag)
        {
            if (i > 0)
            {
                ((EntityLivingBase)entityIn).knockBack(this, (float)i * 0.5F, MathHelper.sin(this.rotationYaw * 0.017453292F), -MathHelper.cos(this.rotationYaw * 0.017453292F));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0)
            {
                entityIn.setFire(j * 4);
            }

            if (entityIn instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)entityIn;
                ItemStack itemstack = this.getHeldItemMainhand();
                ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;

                if (!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.getItem().canDisableShield(itemstack, itemstack1, entityplayer, this) && itemstack1.getItem().isShield(itemstack1, entityplayer))
                {
                    float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

                    if (this.rand.nextFloat() < f1)
                    {
                        entityplayer.getCooldownTracker().setCooldown(itemstack1.getItem(), 100);
                        this.world.setEntityState(entityplayer, (byte)30);
                    }
                }
            }

            this.applyEnchantments(this, entityIn);
        }
        return flag;
    }
}