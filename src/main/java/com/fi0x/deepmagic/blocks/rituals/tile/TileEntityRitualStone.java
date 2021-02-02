package com.fi0x.deepmagic.blocks.rituals.tile;

import com.fi0x.deepmagic.util.IManaTileEntity;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.world.StructureChecker;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

import javax.annotation.Nonnull;

public abstract class TileEntityRitualStone extends TileEntity implements ITickable, IManaTileEntity
{
    protected double storedMana;
    private int sync;
    protected double manaCosts = 20;
    protected int syncTime = 20;

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
    {
        compound.setDouble("stored", storedMana);
        compound.setInteger("sync", sync);
        compound.setDouble("manaCosts", manaCosts);
        compound.setInteger("syncTime", syncTime);

        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        storedMana = compound.getDouble("stored");
        sync = compound.getInteger("sync");
        manaCosts = compound.getDouble("manaCosts");
        syncTime = compound.getInteger("syncTime");

        super.readFromNBT(compound);
    }

    @Override
    public void update()
    {
        sync--;
        if(sync > 0) return;
        sync = syncTime;

        if(hasRedstonePower())
        {
            if(StructureChecker.verifyRitualStructure(world, pos)) syncedUpdate();
        }
    }
    protected void syncedUpdate()
    {
    }

    @Override
    public double getSpaceForMana()
    {
        return Math.max(ConfigHandler.ritualStoneManaCapacity, manaCosts) - storedMana;
    }
    @Override
    public double addManaToStorage(double amount)
    {
        double ret = amount - (Math.max(ConfigHandler.ritualStoneManaCapacity, manaCosts) - storedMana);
        if(ret > 0) storedMana = Math.max(ConfigHandler.ritualStoneManaCapacity, manaCosts);
        else storedMana += amount;
        markDirty();
        return ret > 0 ? ret : 0;
    }

    private boolean hasRedstonePower()
    {
        boolean power = world.isBlockPowered(pos);
        if(!power)
        {
            for(EnumFacing side : EnumFacing.VALUES)
            {
                IBlockState state = world.getBlockState(pos.offset(side));
                if(state.getBlock() == Blocks.REDSTONE_WIRE && state.getValue(BlockRedstoneWire.POWER) > 0) return true;
            }
        }
        return power;
    }
}
