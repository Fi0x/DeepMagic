package com.fi0x.deepmagic.world.generators.trees;

import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenHugeTrees;

import javax.annotation.Nonnull;
import java.util.Random;

public class TreeGenInsanityLarge extends WorldGenHugeTrees
{
    public TreeGenInsanityLarge(boolean notify)
    {
        super(notify, 25, 15, ModBlocks.INSANITY_LOG.getDefaultState(), ModBlocks.INSANITY_LEAVES.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE));
    }

    @Override
    public boolean generate(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos position)
    {
        int height = this.getHeight(rand);
        int crownWidth = rand.nextInt(4) + 4;
        if(!ensureGrowable(worldIn, rand, position, height)) return false;

        createCrown(worldIn, position.up(height), crownWidth);
        createBranches(worldIn, rand, position, height, 8);
        createTrunk(worldIn, position, height);

        return true;
    }
    public void createBranches(World worldIn, Random rand, BlockPos position, int height, int branchLength)
    {
        for(int yCheck = position.getY() + height - 1 - rand.nextInt(4); yCheck > position.getY() + height / 2; yCheck -= 1 + rand.nextInt(3))
        {
            float r = rand.nextFloat() * ((float) Math.PI * 2F);
            int leavesLayerX = position.getX() + (int) (0.5F + MathHelper.cos(r) * 4.0F);
            int leavesLayerZ = position.getZ() + (int) (0.5F + MathHelper.sin(r) * 4.0F);

            for(int logCounter = 0; logCounter < branchLength; logCounter++)
            {
                leavesLayerX = position.getX() + (int) (1.5F + MathHelper.cos(r) * (float) logCounter);
                leavesLayerZ = position.getZ() + (int) (1.5F + MathHelper.sin(r) * (float) logCounter);
                this.setBlockAndNotifyAdequately(worldIn, new BlockPos(leavesLayerX, yCheck - 3 + logCounter / 2, leavesLayerZ), this.woodMetadata);
            }

            int leaveLayers = 1 + rand.nextInt(2);

            for(int leavesLayerY = yCheck - leaveLayers; leavesLayerY <= yCheck + 1; leavesLayerY++)
            {
                int l1 = leavesLayerY - yCheck;
                this.growLeavesLayer(worldIn, new BlockPos(leavesLayerX, leavesLayerY, leavesLayerZ), 5 - l1);
            }
        }
    }

    private void createCrown(World worldIn, BlockPos center, int width)
    {
        for(int j = -3; j <= 0; ++j)
        {
            this.growLeavesLayerStrict(worldIn, center.up(j), width + 1 - j);
        }
    }
    private void createTrunk(World world, BlockPos startPos, int height)
    {
        for(int y = 0; y < height; y++)
        {
            BlockPos centerLog = startPos.up(y);
            if(this.isAirLeaves(world, centerLog))
            {
                this.setBlockAndNotifyAdequately(world, centerLog, this.woodMetadata);
            }

            if(y < height - 2)
            {
                BlockPos adjacentLog = centerLog.east();
                if(this.isAirLeaves(world, adjacentLog))
                {
                    this.setBlockAndNotifyAdequately(world, adjacentLog, this.woodMetadata);
                }

                adjacentLog = centerLog.south().east();
                if(this.isAirLeaves(world, adjacentLog))
                {
                    this.setBlockAndNotifyAdequately(world, adjacentLog, this.woodMetadata);
                }

                adjacentLog = centerLog.south();
                if(this.isAirLeaves(world, adjacentLog))
                {
                    this.setBlockAndNotifyAdequately(world, adjacentLog, this.woodMetadata);
                }
            }
        }
    }

    private boolean isAirLeaves(World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos);
    }
}