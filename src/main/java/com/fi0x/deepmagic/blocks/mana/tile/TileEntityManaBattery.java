package com.fi0x.deepmagic.blocks.mana.tile;

import com.fi0x.deepmagic.util.IManaTileEntity;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class TileEntityManaBattery extends TileEntity implements IManaTileEntity, ITickable
{
    private boolean isCreative = false;
    private final ArrayList<BlockPos> manaTargets = new ArrayList<>();
    private double storedMana;

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
    {
        compound.setBoolean("isCreative", isCreative);

        NBTTagList targets = new NBTTagList();
        for(BlockPos pos : manaTargets)
        {
            NBTTagCompound position = new NBTTagCompound();
            position.setInteger("x", pos.getX());
            position.setInteger("y", pos.getY());
            position.setInteger("z", pos.getZ());
            targets.appendTag(position);
        }
        compound.setTag("targets", targets);

        compound.setDouble("stored", storedMana);

        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        isCreative = compound.getBoolean("isCreative");

        manaTargets.clear();
        NBTTagList targetList = compound.getTagList("targets", Constants.NBT.TAG_COMPOUND);
        for(int i = 0; i < targetList.tagCount(); i++)
        {
            NBTTagCompound position = targetList.getCompoundTagAt(i);
            int x = position.getInteger("x");
            int y = position.getInteger("y");
            int z = position.getInteger("z");
            manaTargets.add(new BlockPos(x, y, z));
        }

        storedMana = compound.getDouble("stored");

        super.readFromNBT(compound);
    }

    @Override
    public void update()
    {
        if(world.isRemote) return;
        boolean dirty = false;

        if(isCreative || storedMana >= 100)
        {
            for(int i = 0; i < manaTargets.size(); i++)
            {
                if(manaTargets.get(i) == null)
                {
                    manaTargets.remove(i);
                    i--;
                    continue;
                }
                TileEntity te = world.getTileEntity(manaTargets.get(i));
                int remainingTargets = manaTargets.size() - i;
                storedMana -= ManaHelper.sendMana(world, manaTargets.get(i), te, storedMana / remainingTargets);
                dirty = true;
            }
        }
        if(dirty)
            markDirty();
    }
    public boolean addOrRemoveTarget(BlockPos pos)
    {
        if(this.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()) > ConfigHandler.manaBlockTransferRange * ConfigHandler.manaBlockTransferRange)
        {
            manaTargets.remove(pos);
            return false;
        }
        if(manaTargets.remove(pos)) return false;
        if(this.pos == pos) return false;
        manaTargets.add(pos);
        return true;
    }
    public boolean hasTargets()
    {
        return !manaTargets.isEmpty();
    }
    public double getStoredMana()
    {
        return storedMana;
    }
    public void makeCreative()
    {
        isCreative = true;
        markDirty();
    }
    public boolean isCreative()
    {
        return isCreative;
    }
    @Override
    public double getSpaceForMana()
    {
        if(isCreative)
            return ConfigHandler.manaBatteryCapacity;
        else
            return ConfigHandler.manaBatteryCapacity - storedMana;
    }
    @Override
    public double addManaToStorage(double amount)
    {
        if(isCreative)
            return 0;
        else
        {
            double ret = amount - (ConfigHandler.manaBatteryCapacity - storedMana);
            if(ret > 0)
                storedMana = ConfigHandler.manaBatteryCapacity;
            else
                storedMana += amount;
            markDirty();
            return ret > 0 ? ret : 0;
        }
    }
}
