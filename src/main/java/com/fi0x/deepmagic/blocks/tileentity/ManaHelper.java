package com.fi0x.deepmagic.blocks.tileentity;

import com.fi0x.deepmagic.util.IManaTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

class ManaHelper
{
    private static boolean isManaTargetValid(World world, BlockPos tePos, BlockPos targetPos, TileEntity te, int senderRange)
    {
        if(targetPos == null) return false;
        if(!(world.getBlockState(targetPos).getBlock() instanceof IManaTileEntity)) return false;
        if(te == null)
        {
            te = world.getTileEntity(targetPos);
            if(te == null) return false;
        }
        return (te.getDistanceSq(tePos.getX(), tePos.getY(), tePos.getZ()) < senderRange * senderRange);
    }

    /**
     * @param world
     * @param sourcePos
     * @param targetPos
     * @param targetTE
     * @param range
     * @param manaAmount
     * @return the amount of mana that was sent to the target
     */
    public static double sendMana(World world, BlockPos sourcePos, BlockPos targetPos, TileEntity targetTE, int range, double manaAmount)
    {
        if(!isManaTargetValid(world, sourcePos, targetPos, targetTE, range)) return 0;

        targetTE = world.getTileEntity(targetPos);
        assert targetTE != null;
        double spaceInTarget = ((IManaTileEntity) targetTE).getSpaceForMana();
        if(spaceInTarget > manaAmount)
        {
            return manaAmount - ((IManaTileEntity) targetTE).addManaToStorage(manaAmount);
        } else
        {
            return spaceInTarget - ((IManaTileEntity) targetTE).addManaToStorage(spaceInTarget);
        }
    }
}