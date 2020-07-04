package com.fi0x.deepmagic.entities.ai;

import com.fi0x.deepmagic.entities.EntityDwarf;
import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class EntityAIMining extends EntityAIBase
{
    protected final EntityDwarf creature;
    private BlockPos destination;
    private final World world;
    private final ArrayList<IBlockState> mineableBlocks;

    public EntityAIMining(EntityDwarf creature, World world)
    {
        this.creature = creature;
        this.setMutexBits(1);
        this.world = world;

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

    @Override
    public boolean shouldExecute()
    {
        return !creature.isMining && creature.posY < 50;
    }

    @Override
    public void startExecuting()
    {
//        this.creature.isMining = true;
//        destination = getRandomDestination();
//
//        ArrayList<BlockPos> miningBlocks = getMiningPath(creature.getPosition(), destination);
//        if(isMineablePath(world, miningBlocks)) mineBlocks(world, miningBlocks, creature);
//        else creature.isMining = false;
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        if(creature.getPosition() == destination) creature.isMining = false;
        return creature.isMining;
    }

    private BlockPos getRandomDestination()
    {
        return new BlockPos(creature.posX + (Math.random() * 20) - 10, creature.posY, creature.posZ + (Math.random() * 20) - 10);
    }

    public ArrayList<BlockPos> getMiningPath(BlockPos currentPosition, BlockPos destination)
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
        for (BlockPos block : blocks)
        {
            if (!mineableBlocks.contains(world.getBlockState(block))) return false;
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