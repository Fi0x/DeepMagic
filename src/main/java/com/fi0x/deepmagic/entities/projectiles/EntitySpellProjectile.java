package com.fi0x.deepmagic.entities.projectiles;

import com.fi0x.deepmagic.items.spells.CastHelper;
import com.fi0x.deepmagic.items.spells.ISpellPart;
import com.fi0x.deepmagic.items.spells.SpellPartHandler;
import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import com.fi0x.deepmagic.items.spells.types.ISpellType;
import com.fi0x.deepmagic.particlesystem.ParticleEnum;
import com.fi0x.deepmagic.particlesystem.ParticleSpawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Objects;

public class EntitySpellProjectile extends EntityThrowable
{
    EntityLivingBase caster;
    public ArrayList<ISpellPart> applicableParts = new ArrayList<>();
    public ArrayList<ArrayList<ISpellPart>> remainingSections = new ArrayList<>();

    public double existingSeconds = 5;

    public EntitySpellProjectile(World worldIn)
    {
        super(worldIn);
        setNoGravity(true);
    }
    public EntitySpellProjectile(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
        setNoGravity(true);
    }
    public EntitySpellProjectile(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
        setNoGravity(true);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        if(ticksExisted > 20 * existingSeconds) setDead();
        double x = posX + Math.random() - 0.5;
        double y = posY + Math.random() - 0.5;
        double z = posZ + Math.random() - 0.5;
        ParticleSpawner.spawnParticle(ParticleEnum.SPELL_PROJECTILE, x, y, z, 0, 0, 0, Math.random() + 0.5, false, 64);
    }
    @Override
    protected void onImpact(@Nonnull RayTraceResult result)//TODO: Test
    {
        setDead();
        EntityLivingBase target = null;
        BlockPos position = null;

        switch(result.typeOfHit)
        {
            case MISS:
                return;
            case BLOCK:
                position = result.getBlockPos();
                break;
            case ENTITY:
                Entity hitTarget = result.entityHit;
                position = hitTarget.getPosition();
                if(hitTarget instanceof EntityLivingBase) target = (EntityLivingBase) hitTarget;
                break;
        }

        executeSpell(target, position);
    }
    @Override
    protected float getGravityVelocity()
    {
        if(hasNoGravity()) return 0;
        else return super.getGravityVelocity();
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
    {
        if(caster != null) compound.setUniqueId("casterUUID", caster.getUniqueID());

        StringBuilder parts = new StringBuilder();
        if(!applicableParts.isEmpty())
        {
            for(ISpellPart part : applicableParts)
            {
                parts.append(":").append(part.getPartAsString());
            }
        }
        compound.setString("applicableParts", parts.toString());

        parts = new StringBuilder();
        if(!remainingSections.isEmpty())
        {
            for(ArrayList<ISpellPart> section : remainingSections)
            {
                parts.append("_:_");
                if(section.isEmpty()) continue;
                for(ISpellPart part : section)
                {
                    parts.append(":").append(part.getPartAsString());
                }
            }
        }
        compound.setString("remainingSections", parts.toString());

        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        if(compound.hasKey("casterUUID")) caster = world.getPlayerEntityByUUID(Objects.requireNonNull(compound.getUniqueId("casterUUID")));

        applicableParts = new ArrayList<>();
        if(compound.hasKey("applicableParts"))
        {
            String parts = compound.getString("applicableParts");
            applicableParts.addAll(SpellPartHandler.getSectionParts(parts));
        }

        remainingSections = new ArrayList<>();
        if(compound.hasKey("remainingSections"))
        {
            String[] sections = compound.getString("remainingSections").split("_:_");
            for(String section : sections)
            {
                if(section.isEmpty()) continue;
                remainingSections.add(SpellPartHandler.getSectionParts(section));
            }
        }

        super.readFromNBT(compound);
    }

    public void setSpell(@Nullable EntityLivingBase caster, ArrayList<ISpellPart> applicableParts, ArrayList<ArrayList<ISpellPart>> remainingSections)
    {
        applicableParts.remove(0);

        this.caster = caster;
        this.applicableParts = applicableParts;
        this.remainingSections = remainingSections;
    }
    public void executeSpell(@Nullable EntityLivingBase target, BlockPos pos)
    {
        boolean executed = false;

        while(!applicableParts.isEmpty())
        {
            if(applicableParts.get(0) instanceof ISpellEffect)
            {
                ((ISpellEffect) applicableParts.get(0)).applyEffect(caster, pos, world);
                if(target != null) ((ISpellEffect) applicableParts.get(0)).applyEffect(caster, target);
            } else if(applicableParts.get(0) instanceof ISpellType)
            {
                ((ISpellType) applicableParts.get(0)).execute(applicableParts, remainingSections, pos, caster, world);
                executed = true;
            }
            if(executed) break;
            applicableParts.remove(0);
        }

        if(!executed) new CastHelper().findAndCastNextSpellType(remainingSections, pos, caster, world);

    }
}