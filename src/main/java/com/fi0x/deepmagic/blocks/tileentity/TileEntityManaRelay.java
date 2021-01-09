package com.fi0x.deepmagic.blocks.tileentity;

import com.fi0x.deepmagic.util.IManaTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class TileEntityManaRelay extends TileEntity implements ITickable, IManaTileEntity
{
    private final ArrayList<BlockPos> manaTargets = new ArrayList<>();

    @Override
    public void update()
    {
        if(world.isRemote) return;

        boolean dirty = false;

        if(dirty) markDirty();
    }
    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound)
    {
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

        return super.writeToNBT(compound);
    }
    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
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

        super.readFromNBT(compound);
    }

    public boolean addOrRemoveTarget(BlockPos pos)
    {
        if(manaTargets.remove(pos)) return false;
        manaTargets.add(pos);
        return true;
    }
}
