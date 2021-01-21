package com.fi0x.deepmagic.blocks.tileentity;

import com.fi0x.deepmagic.items.spells.CastHelper;
import com.fi0x.deepmagic.items.spells.ISpellPart;
import com.fi0x.deepmagic.items.spells.SpellPartHandler;
import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import com.fi0x.deepmagic.items.spells.types.ISpellType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class TileEntityRune extends TileEntity
{
    EntityLivingBase caster;
    ArrayList<ISpellPart> applicableParts = new ArrayList<>();
    ArrayList<ArrayList<ISpellPart>> remainingSections = new ArrayList<>();
    int remainingCasts = 1;

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
    {
        if(caster != null) compound.setTag("casterUUID", NBTUtil.createUUIDTag(caster.getUniqueID()));

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

        compound.setInteger("remainingCasts", remainingCasts);

        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound)
    {
        if(compound.hasKey("casterUUID")) caster = world.getPlayerEntityByUUID(NBTUtil.getUUIDFromTag(compound.getCompoundTag("casterUUID")));

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

        remainingCasts = compound.getInteger("remainingCasts");

        super.readFromNBT(compound);
    }

    public void setSpell(@Nullable EntityLivingBase caster, ArrayList<ISpellPart> applicableParts, ArrayList<ArrayList<ISpellPart>> remainingSections)
    {
        remainingCasts = (int) applicableParts.get(0).getDuration();
        applicableParts.remove(0);

        this.caster = caster;
        this.applicableParts = applicableParts;
        this.remainingSections = remainingSections;
    }
    public void executeSpell(EntityLivingBase target)
    {
        boolean executed = false;

        while(!applicableParts.isEmpty())
        {
            if(applicableParts.get(0) instanceof ISpellEffect)
            {
                ((ISpellEffect) applicableParts.get(0)).applyEffect(caster, pos, world);
                ((ISpellEffect) applicableParts.get(0)).applyEffect(caster, target);
            } else if(applicableParts.get(0) instanceof ISpellType)
            {
                ((ISpellType) applicableParts.get(0)).execute(applicableParts, remainingSections, pos, caster, world);
                executed = true;
            }
            if(executed) break;
            applicableParts.remove(0);
        }

        if(!executed)
        {
            new CastHelper().findAndCastNextSpellType(remainingSections, pos, caster, world);
        }
        remainingCasts--;
        if(remainingCasts <= 0) world.setBlockToAir(pos);
    }
}
