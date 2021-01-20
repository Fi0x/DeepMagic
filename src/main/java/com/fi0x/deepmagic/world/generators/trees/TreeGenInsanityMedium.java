package com.fi0x.deepmagic.world.generators.trees;

import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nonnull;
import java.util.Random;

public class TreeGenInsanityMedium extends WorldGenAbstractTree
{
    //TODO: Adjust size and shape of tree to look like large oak trees
    private final IBlockState WOOD = ModBlocks.INSANITY_LOG.getDefaultState();
    private final IBlockState LEAVES = ModBlocks.INSANITY_LEAVES.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE);
    private final int MIN_HEIGHT = 7;
    private final int HEIGHT_VARIATION = 5;
    private final int TOP_WIDTH = 3;
    private final int CROWN_MIN_HEIGHT = 2;
    private final int DEFAULT_EXTRA_SPACE = 2;

    public TreeGenInsanityMedium(boolean notify)
    {
        super(notify);
    }

    @Override
    public boolean generate(@Nonnull World worldIn, Random rand, BlockPos pos)
    {
        int actualHeight = rand.nextInt(HEIGHT_VARIATION) + MIN_HEIGHT;

        if(pos.getY() < 1 || pos.getY() + actualHeight + 1 >= worldIn.getHeight()) return false;
        if(!isSuitableLocation(worldIn, pos, actualHeight)) return false;

        IBlockState groundState = worldIn.getBlockState(pos.down());
        if(!groundState.getBlock().canSustainPlant(groundState, worldIn, pos.down(), EnumFacing.UP, (IPlantable) Blocks.SAPLING) || pos.getY() >= worldIn.getHeight() - actualHeight) return false;

        groundState.getBlock().onPlantGrow(groundState, worldIn, pos.down(), pos);
        generateLeaves(worldIn, pos, actualHeight, rand);
        generateTrunk(worldIn, pos, actualHeight);
        return true;
    }

    private void generateLeaves(World world, BlockPos pos, int height, Random rand)
    {
        for(int foliageY = pos.getY() + height - (rand.nextInt(TOP_WIDTH) + CROWN_MIN_HEIGHT); foliageY <= pos.getY() + height; ++foliageY)
        {
            int foliageLayer = foliageY - (pos.getY() + height);
            int foliageLayerRadius = 1 - foliageLayer / 2;

            for(int foliageX = pos.getX() - foliageLayerRadius; foliageX <= pos.getX() + foliageLayerRadius; ++foliageX)
            {
                int foliageRelativeX = foliageX - pos.getX();

                for(int foliageZ = pos.getZ() - foliageLayerRadius; foliageZ <= pos.getZ() + foliageLayerRadius; ++foliageZ)
                {
                    int foliageRelativeZ = foliageZ - pos.getZ();

                    // Fill in layer with some randomness
                    if(Math.abs(foliageRelativeX) != foliageLayerRadius || Math.abs(foliageRelativeZ) != foliageLayerRadius || rand.nextInt(2) != 0 && foliageLayer != 0)
                    {
                        BlockPos blockPos = new BlockPos(foliageX, foliageY, foliageZ);
                        IBlockState state = world.getBlockState(blockPos);

                        if(state.getBlock().isAir(state, world, blockPos) || state.getBlock().isLeaves(state, world, blockPos))
                        {
                            setBlockAndNotifyAdequately(world, blockPos, LEAVES);
                        }
                    }
                }
            }
        }
    }

    private void generateTrunk(World world, BlockPos pos, int minHeight)
    {
        for(int height = 0; height < minHeight; ++height)
        {
            BlockPos upN = pos.up(height);
            IBlockState state = world.getBlockState(upN);

            if(state.getBlock().isAir(state, world, upN) || state.getBlock().isLeaves(state, world, upN))
            {
                setBlockAndNotifyAdequately(world, pos.up(height), WOOD.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y));
            }
        }
    }

    private boolean isSuitableLocation(World world, BlockPos pos, int minHeight)
    {
        boolean isSuitableLocation = true;

        for(int checkY = pos.getY(); checkY <= pos.getY() + minHeight; checkY++)
        {
            int extraSpaceNeeded = DEFAULT_EXTRA_SPACE;
            if(checkY == pos.getY())
            {
                extraSpaceNeeded = 0;
            }

            if(checkY >= pos.getY() + minHeight - CROWN_MIN_HEIGHT)
            {
                extraSpaceNeeded = TOP_WIDTH;
            }

            BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();

            for(int checkX = pos.getX() - extraSpaceNeeded; checkX <= pos.getX() + extraSpaceNeeded && isSuitableLocation; checkX++)
            {
                for(int checkZ = pos.getZ() - extraSpaceNeeded; checkZ <= pos.getZ() + extraSpaceNeeded && isSuitableLocation; checkZ++)
                {
                    isSuitableLocation = isReplaceable(world, blockPos.setPos(checkX, checkY, checkZ));
                }
            }
        }
        return isSuitableLocation;
    }
}