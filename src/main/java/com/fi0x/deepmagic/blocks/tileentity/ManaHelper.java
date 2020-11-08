package com.fi0x.deepmagic.blocks.tileentity;

import com.fi0x.deepmagic.blocks.mana.ManaAltar;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

class ManaHelper
{
    public static boolean isAltarValid(World world, BlockPos tePos, BlockPos linkedAltarPos, TileEntityManaAltar linkedAltar)
    {
        if(linkedAltarPos == null) return false;
        if(!(world.getBlockState(linkedAltarPos).getBlock() instanceof ManaAltar)) return false;
        if(linkedAltar == null)
        {
            linkedAltar = (TileEntityManaAltar) world.getTileEntity(linkedAltarPos);
            if(linkedAltar == null) return false;
        }
        return (linkedAltar.getDistanceSq(tePos.getX(), tePos.getY(), tePos.getZ()) < ConfigHandler.manaBlockTransferRange * ConfigHandler.manaBlockTransferRange);
    }
}