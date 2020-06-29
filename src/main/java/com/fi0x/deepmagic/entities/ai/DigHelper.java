package com.fi0x.deepmagic.entities.ai;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class DigHelper
{
    public static ArrayList<BlockPos> getMiningBlocks(World world, BlockPos currentPosition, BlockPos destination)
    {
        ArrayList<BlockPos> blocks = new ArrayList<>();
        int xDifference = destination.getX() - currentPosition.getX();
        int zDifference = destination.getZ() - currentPosition.getZ();

        BlockPos checkPos = currentPosition;
        for(int i = 0; i < Math.abs(xDifference); i++)
        {
            for(int j = 0; j < Math.abs(zDifference); j++)
            {
                IBlockState currentBlock = world.getBlockState(checkPos);
                if(currentBlock != Blocks.AIR.getDefaultState()) blocks.add(checkPos);

                currentBlock = world.getBlockState(checkPos.add(0, 1, 0));
                if(currentBlock != Blocks.AIR.getDefaultState()) blocks.add(checkPos);

                //TODO: Add correct values
                checkPos = checkPos.add(0, 0, 0);
            }
            //TODO: Add correct values
            checkPos = checkPos.add(0, 0, 0);
        }
        return blocks;
    }
}