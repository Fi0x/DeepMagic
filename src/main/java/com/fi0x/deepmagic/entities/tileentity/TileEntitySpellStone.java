package com.fi0x.deepmagic.entities.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySpellStone extends TileEntity
{
    private int attackDmg;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("attackDmg", attackDmg);
        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        attackDmg = compound.getInteger("attackDmg");
        super.readFromNBT(compound);
    }

    public int getAttackDmg()
    {
        return attackDmg;
    }
    public void setAttackDmg(int x)
    {
        attackDmg = x;
        markDirty();
    }
    public void resetAttackDmg()
    {
        attackDmg = 0;
        markDirty();
    }
}
