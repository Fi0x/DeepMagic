package com.fi0x.deepmagic.blocks.tileentity;

import com.fi0x.deepmagic.util.IManaTileEntity;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Objects;

public class TileEntityManaRelay extends TileEntity implements IManaTileEntity
{
    private boolean unlimitedRange = false;
    private final ArrayList<BlockPos> manaTargets = new ArrayList<>();
    private double manaBuffer;
    private double storedMana;

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
    {
        compound.setBoolean("range", unlimitedRange);

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

        compound.setDouble("buffer", manaBuffer);
        compound.setDouble("stored", storedMana);

        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        unlimitedRange = compound.getBoolean("range");

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

        manaBuffer = compound.getDouble("buffer");
        storedMana = compound.getDouble("stored");

        super.readFromNBT(compound);
    }

    public boolean removeRangeLimit()
    {
        if(unlimitedRange) return false;
        unlimitedRange = true;
        return true;
    }
    public boolean addOrRemoveTarget(BlockPos pos)
    {
        if(!unlimitedRange || this.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()) > ConfigHandler.manaBlockTransferRange * ConfigHandler.manaBlockTransferRange)
        {
            manaTargets.remove(pos);
            return false;
        }
        if(manaTargets.remove(pos)) return false;
        manaTargets.add(pos);
        return true;
    }
    public boolean hasTargets()
    {
        return !manaTargets.isEmpty();
    }
    @Override
    public double getSpaceForMana()
    {
        manaBuffer = 0;
        for(BlockPos pos : manaTargets)
        {
            manaBuffer += ((IManaTileEntity) Objects.requireNonNull(world.getTileEntity(pos))).getSpaceForMana();
        }
        return manaBuffer;
    }
    @Override
    public double addManaToStorage(double amount)
    {
        storedMana += amount;

        for(int i = 0; i < manaTargets.size(); i++)
        {
            TileEntity te = world.getTileEntity(manaTargets.get(i));
            int remainingTargets = manaTargets.size() - i;
            storedMana -= ManaHelper.sendMana(world, manaTargets.get(i), te, storedMana / remainingTargets);
        }
        double ret = storedMana;
        storedMana = 0;
        return ret > 0 ? ret : 0;
    }
}
