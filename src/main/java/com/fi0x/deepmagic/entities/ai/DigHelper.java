package com.fi0x.deepmagic.entities.ai;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class DigHelper
{
    private static final IBlockState[] mineableBlocks = new IBlockState[] {
            Blocks.STONE.getDefaultState(),
            Blocks.DIRT.getDefaultState(),
            Blocks.QUARTZ_ORE.getDefaultState(),
            Blocks.COAL_ORE.getDefaultState(),
            Blocks.IRON_ORE.getDefaultState(),
            Blocks.GOLD_ORE.getDefaultState(),
            Blocks.REDSTONE_ORE.getDefaultState(),
            Blocks.LAPIS_ORE.getDefaultState(),
            Blocks.DIAMOND_ORE.getDefaultState(),
            Blocks.EMERALD_ORE.getDefaultState()};

    public static ArrayList<BlockPos> getMiningBlocks(World world, BlockPos currentPosition, BlockPos destination)
    {
        ArrayList<BlockPos> blocks = new ArrayList<>();
        int xDifference = destination.getX() - currentPosition.getX();
        int zDifference = destination.getZ() - currentPosition.getZ();
        int xIncrease = 1;
        int zIncrease = 1;
        if(destination.getX() > currentPosition.getX()) xIncrease = -1;
        if(destination.getZ() > currentPosition.getZ()) zIncrease = -1;

        BlockPos checkPos = currentPosition;
        for(int i = 0; i < Math.abs(xDifference); i++)
        {
            if(isMiningBlock(world.getBlockState(checkPos)) && isMiningBlock(world.getBlockState(checkPos.add(0, 1, 0))))
            {
                blocks.add(checkPos.add(0, 1, 0));
                blocks.add(checkPos);
            }
            checkPos = checkPos.add(xIncrease, 0, 0);
        }
        for(int j = 0; j < Math.abs(zDifference); j++)
        {
            if(isMiningBlock(world.getBlockState(checkPos)) && isMiningBlock(world.getBlockState(checkPos.add(0, 1, 0))))
            {
                blocks.add(checkPos.add(0, 1, 0));
                blocks.add(checkPos);
            }

            checkPos = checkPos.add(0, 0, zIncrease);
        }
        return blocks;
    }

    private static boolean isMiningBlock(IBlockState checkBlock)
    {
        for(int i = 0; i < mineableBlocks.length; i++)
        {
            if(checkBlock == mineableBlocks[i]) return true;
        }
        return false;
    }

    public static void mineBlocks(World world, ArrayList<BlockPos> miningBlocks, EntityCreature creature)
    {
        while(!miningBlocks.isEmpty())
        {
            BlockPos currentPosition = miningBlocks.get(0);
            Block currentBlock = world.getBlockState(currentPosition).getBlock();
            currentBlock.dropBlockAsItem(world, miningBlocks.get(0), currentBlock.getDefaultState(), 2);
            world.setBlockToAir(currentPosition);
            creature.getNavigator().tryMoveToXYZ(currentPosition.getX(), currentPosition.getY(), currentPosition.getZ(), 1);

            miningBlocks.remove(0);
        }
    }
}