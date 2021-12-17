package com.fi0x.deepmagic.blocks.rituals.tile;

import com.fi0x.deepmagic.blocks.rituals.RITUAL_TYPE;
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
    protected RITUAL_TYPE type;
    protected double storedMana;
    private int sync;
    protected double manaCosts = 20;
    protected int syncTime = 20;
    protected boolean manaOnSync = true;
    protected boolean needsRedstone = true;

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
    {
        compound.setInteger("ritualType", type.ordinal());
        compound.setDouble("stored", storedMana);
        compound.setInteger("sync", sync);
        compound.setDouble("manaCosts", manaCosts);
        compound.setInteger("syncTime", syncTime);
        compound.setBoolean("manaOnSync", manaOnSync);
        compound.setBoolean("redstoneMode", needsRedstone);

        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        type = RITUAL_TYPE.values()[compound.getInteger("ritualType")];
        storedMana = compound.getDouble("stored");
        sync = compound.getInteger("sync");
        manaCosts = compound.getDouble("manaCosts");
        syncTime = compound.getInteger("syncTime");
        manaOnSync = compound.getBoolean("manaOnSync");
        needsRedstone = compound.getBoolean("redstoneMode");

        super.readFromNBT(compound);
    }

    @Override
    public void update()
    {
        if(!ConfigHandler.allowRituals) return;

        sync--;
        if(sync > 0) return;
        sync = syncTime;

        if(!needsRedstone || hasRedstonePower())
        {
            if(StructureChecker.verifyRitualStructure(world, pos, type))
            {
                if(manaOnSync)
                {
                    if(storedMana < manaCosts) return;

                    storedMana -= manaCosts;
                    markDirty();
                }
                syncedUpdate();
            }
        }
    }
    protected void syncedUpdate()
    {
    }

    public boolean changeRedstoneMode()
    {
        needsRedstone = !needsRedstone;
        return needsRedstone;
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
