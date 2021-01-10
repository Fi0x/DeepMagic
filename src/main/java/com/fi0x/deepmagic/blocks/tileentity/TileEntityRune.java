package com.fi0x.deepmagic.blocks.tileentity;

import com.fi0x.deepmagic.items.spells.ISpellPart;
import com.fi0x.deepmagic.items.spells.SpellPartHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class TileEntityRune extends TileEntity
{
    EntityLivingBase caster;
    ArrayList<ISpellPart> applicableParts = new ArrayList<>();
    ArrayList<ArrayList<ISpellPart>> remainingSections = new ArrayList<>();

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
                parts.append(":").append(part.getName());
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
                    parts.append(":").append(part.getName());
                }
            }
        }
        compound.setString("remainingSections", parts.toString());

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

        super.readFromNBT(compound);
    }

    public void setSpell(EntityLivingBase caster, ArrayList<ISpellPart> applicableParts, ArrayList<ArrayList<ISpellPart>> remainingSections)
    {
        applicableParts.remove(0);

        this.caster = caster;
        this.applicableParts = applicableParts;
        this.remainingSections = remainingSections;
    }
    public void executeSpell(EntityLivingBase target)
    {

        //TODO: Cast spell
    }
}
