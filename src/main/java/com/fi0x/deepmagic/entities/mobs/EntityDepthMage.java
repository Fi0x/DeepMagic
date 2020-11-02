package com.fi0x.deepmagic.entities.mobs;

import com.fi0x.deepmagic.entities.projectiles.EntitySpellExplosion;
import com.fi0x.deepmagic.entities.projectiles.EntitySpellFireball;
import com.fi0x.deepmagic.network.PacketReturnMobAnimation;
import com.fi0x.deepmagic.util.CustomNameGenerator;
import com.fi0x.deepmagic.util.IMagicCreature;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.util.handlers.LootTableHandler;
import com.fi0x.deepmagic.util.handlers.PacketHandler;
import com.fi0x.deepmagic.util.handlers.SoundsHandler;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
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

public class EntityDepthMage extends EntityCreature implements IRangedAttackMob, IMagicCreature
{
    public int attackTimer;
    public int defenceTime;

    public EntityDepthMage(World worldIn)
    {
        super(worldIn);
        this.setSize(1F, 2F);
        attackTimer = 0;
        defenceTime = 0;
        this.setCustomNameTag(CustomNameGenerator.getRandomMageName());
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackRanged(this, 1.0D, 40, 20.0F));
        this.tasks.addTask(3, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));

        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityMob.class, false));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ConfigHandler.healthDepthMage);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ConfigHandler.damageDepthMage);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundsHandler.ENTITY_DEPTH_MAGE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSourceIn)
    {
        return SoundsHandler.ENTITY_DEPTH_MAGE_HURT;
    }
    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundsHandler.ENTITY_DEPTH_MAGE_DEATH;
    }
    @Override
    protected void playStepSound(@Nonnull BlockPos pos, @Nonnull Block blockIn)
    {
        this.playSound(SoundsHandler.ENTITY_DEPTH_MAGE_STEP, 1F, 1F);
    }
    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return LootTableHandler.DEPTH_MAGE;
    }
    @Override
    public float getEyeHeight()
    {
        return 1.75F;
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
    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
        launchProjectileToCoords(target.posX, target.posY + target.getEyeHeight()*0.5, target.posZ);
        attackTimer = 20;
    }
    @Override
    public void setSwingingArms(boolean swingingArms)
    {
    }
    @Override
    public void onUpdate()
    {
        super.onUpdate();
        if(attackTimer <= 0) return;
        attackTimer--;
        PacketHandler.INSTANCE.sendToAllAround(new PacketReturnMobAnimation(this.getEntityId(), attackTimer, defenceTime), new NetworkRegistry.TargetPoint(world.provider.getDimension(), posX, posY, posZ, 64));
    }

    private void launchProjectileToCoords(double x, double y, double z)
    {
        this.world.playEvent(null, 1024, new BlockPos(this), 0);
        double d0 = getPosition().getX();
        double d1 = getPosition().getY() + getEyeHeight();
        double d2 = getPosition().getZ();
        double d3 = x - d0;
        double d4 = y - d1;
        double d5 = z - d2;
        Entity projectile;
        int rand = (int) (Math.random()*2);
        if(rand == 0) projectile = new EntitySpellFireball(world, this, d3, d4, d5);
        else projectile = new EntitySpellExplosion(world, this, d3, d4, d5);

        projectile.setEntityInvulnerable(true);

        projectile.posY = d1;
        projectile.posX = d0;
        projectile.posZ = d2;
        this.world.spawnEntity(projectile);
    }
}