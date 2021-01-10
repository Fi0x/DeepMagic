package com.fi0x.deepmagic.blocks.tileentity;

import com.fi0x.deepmagic.items.spells.ISpellPart;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class TileEntityRune extends TileEntity
{
    EntityLivingBase caster;
    ArrayList<ISpellPart> applicableParts;
    ArrayList<ArrayList<ISpellPart>> remainingSections;

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
    {
        //TODO: Save caster, applicableParts and remainingSections
        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound)
    {
        //TODO: Load caster, applicableParts and remainingSections
        super.readFromNBT(compound);
    }

    public void setSpell(EntityLivingBase caster, ArrayList<ISpellPart> applicableParts, ArrayList<ArrayList<ISpellPart>> remainingSections)
    {
        applicableParts.remove(0);

        this.caster = caster;
        this.applicableParts = applicableParts;
        this.remainingSections = remainingSections;
    }
    public void executeSpell()
    {
        //TODO: Cast spell
    }
}
