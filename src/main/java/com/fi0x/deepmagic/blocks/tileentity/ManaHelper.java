package com.fi0x.deepmagic.blocks.tileentity;

import com.fi0x.deepmagic.util.IManaTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

class ManaHelper
{
    private static boolean isManaTargetValid(World world, BlockPos targetPos, TileEntity te)
    {
        if(targetPos == null) return false;
        if(!(world.getTileEntity(targetPos) instanceof IManaTileEntity)) return false;
        if(te == null)
        {
            te = world.getTileEntity(targetPos);
            return te != null;
        }
        return true;
    }

    /**
     * @param world
     * @param targetPos
     * @param targetTE
     * @param manaAmount
     * @return the amount of mana that was sent to the target
     */
    public static double sendMana(World world, BlockPos targetPos, TileEntity targetTE, double manaAmount)
    {
        if(!isManaTargetValid(world, targetPos, targetTE)) return 0;

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