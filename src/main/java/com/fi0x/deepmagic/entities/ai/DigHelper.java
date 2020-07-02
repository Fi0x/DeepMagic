package com.fi0x.deepmagic.entities.ai;

import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class DigHelper
{
    private final ArrayList<IBlockState> mineableBlocks;

    public DigHelper()
    {
        mineableBlocks = new ArrayList<>();
        mineableBlocks.add(Blocks.STONE.getDefaultState());
        mineableBlocks.add(Blocks.DIRT.getDefaultState());
        mineableBlocks.add(Blocks.AIR.getDefaultState());
        mineableBlocks.add(Blocks.QUARTZ_ORE.getDefaultState());
        mineableBlocks.add(Blocks.COAL_ORE.getDefaultState());
        mineableBlocks.add(Blocks.IRON_ORE.getDefaultState());
        mineableBlocks.add(Blocks.GOLD_ORE.getDefaultState());
        mineableBlocks.add(Blocks.REDSTONE_ORE.getDefaultState());
        mineableBlocks.add(Blocks.LAPIS_ORE.getDefaultState());
        mineableBlocks.add(Blocks.DIAMOND_ORE.getDefaultState());
        mineableBlocks.add(Blocks.EMERALD_ORE.getDefaultState());
        mineableBlocks.add(ModBlocks.DEEP_CRYSTAL_ORE.getDefaultState());
    }

    public ArrayList<BlockPos> getMiningPath(World world, BlockPos currentPosition, BlockPos destination)
    {
        ArrayList<BlockPos> blocks = new ArrayList<>();
        int xIncrease = 1;
        int zIncrease = 1;
        if(destination.getX() > currentPosition.getX()) xIncrease = -1;
        if(destination.getZ() > currentPosition.getZ()) zIncrease = -1;

        BlockPos checkPos = currentPosition;
        while(checkPos.getX() != destination.getX())
        {
            checkPos = checkPos.add(xIncrease, 0, 0);
            blocks.add(checkPos.add(0, 1, 0));
            blocks.add(checkPos);
        }
        while(checkPos.getZ() != destination.getZ())
        {
            checkPos = checkPos.add(0, 0, zIncrease);
            blocks.add(checkPos.add(0, 1, 0));
            blocks.add(checkPos);
        }

        return blocks;
    }

    public boolean isMineablePath(World world, ArrayList<BlockPos> blocks)
    {
        for(int i = 0; i < blocks.size(); i++)
        {
            if(!mineableBlocks.contains(world.getBlockState(blocks.get(i))))
            {
                return false;
            }
        }
        return true;
    }

    public void mineBlocks(World world, ArrayList<BlockPos> miningBlocks, EntityCreature creature)
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