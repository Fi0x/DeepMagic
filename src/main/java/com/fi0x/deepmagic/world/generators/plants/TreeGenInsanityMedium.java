package com.fi0x.deepmagic.world.generators.plants;

import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import javax.annotation.Nonnull;
import java.util.Random;

public class TreeGenInsanityMedium extends WorldGenAbstractTree
{
    private final IBlockState WOOD = ModBlocks.INSANITY_LOG.getDefaultState();
    private final IBlockState LEAVES = ModBlocks.INSANITY_LEAVES.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE);

    public TreeGenInsanityMedium(boolean notify)
    {
        super(notify);
    }

    @Override
    public boolean generate(@Nonnull World world, Random rand, @Nonnull BlockPos position)
    {
        int treeHeight = rand.nextInt(6) + 9;
        if(!isValidLocation(world, position, treeHeight)) return false;

        IBlockState state = world.getBlockState(position.down());

        state.getBlock().onPlantGrow(state, world, position.down(), position);
        EnumFacing mainDirection = EnumFacing.Plane.HORIZONTAL.random(rand);
        int branchingHeight = treeHeight - treeHeight / 2 + rand.nextInt(treeHeight / 4);

        BlockPos crownPosition = createMainTrunk(world, rand, position, mainDirection, treeHeight, branchingHeight);
        createCrown(world, crownPosition, 4, true);

        int xPos = position.getX();
        int zPos = position.getZ();
        EnumFacing secondaryDirection = EnumFacing.Plane.HORIZONTAL.random(rand);

        if(secondaryDirection != mainDirection) createSecondTrunk(world, rand, position, treeHeight, secondaryDirection, branchingHeight, xPos, zPos);

        return true;
    }

    private BlockPos createMainTrunk(World world, Random rand, BlockPos position, EnumFacing branchDirection, int treeHeight, int branchingHeight)
    {
        int xPos = position.getX();
        int zPos = position.getZ();
        int highestLeafPos = 0;
        int branchSideOffset = 3 - rand.nextInt(3);

        for(int heightIterator = 0; heightIterator < treeHeight; heightIterator++)
        {
            int currentTreeY = position.getY() + heightIterator;
            if(heightIterator >= branchingHeight && branchSideOffset > 0)
            {
                xPos += branchDirection.getFrontOffsetX();
                zPos += branchDirection.getFrontOffsetZ();
                --branchSideOffset;
            }

            BlockPos logPosition = new BlockPos(xPos, currentTreeY, zPos);
            IBlockState state = world.getBlockState(logPosition);

            if(state.getBlock().isAir(state, world, logPosition) || state.getBlock().isLeaves(state, world, logPosition))
            {
                setBlockAndNotifyAdequately(world, logPosition, WOOD);
                highestLeafPos = currentTreeY;
            }
        }

        return new BlockPos(xPos, highestLeafPos, zPos);
    }
    private void createSecondTrunk(World world, Random rand, BlockPos treePos, int treeHeight, EnumFacing direction, int branchHeight, int xPos, int zPos)
    {
        int branchStart = branchHeight - rand.nextInt(branchHeight);
        int trunkLength = 5 + rand.nextInt(3);
        int highestLeafPos = 0;

        for(int yOffset = branchStart; yOffset < treeHeight && trunkLength > 0; trunkLength--)
        {
            if(yOffset >= 1)
            {
                int logYPos = treePos.getY() + yOffset;
                xPos += direction.getFrontOffsetX();
                zPos += direction.getFrontOffsetZ();
                BlockPos blockpos1 = new BlockPos(xPos, logYPos, zPos);
                IBlockState state = world.getBlockState(blockpos1);

                if(state.getBlock().isAir(state, world, blockpos1) || state.getBlock().isLeaves(state, world, blockpos1))
                {
                    setBlockAndNotifyAdequately(world, blockpos1, WOOD);
                    highestLeafPos = logYPos;
                }
            }
            yOffset++;
        }

        if(highestLeafPos > 0) createCrown(world, new BlockPos(xPos, highestLeafPos, zPos), 3, false);

    }
    private void createCrown(World world, BlockPos leafCenter, int leafRadius, boolean isTop)
    {
        for(int offsetX = -leafRadius; offsetX <= leafRadius; offsetX++)
        {
            for(int offsetZ = -leafRadius; offsetZ <= leafRadius; offsetZ++)
            {
                if(Math.abs(offsetX) != leafRadius || Math.abs(offsetZ) != leafRadius)
                {
                    this.placeLeafAt(world, leafCenter.add(offsetX, 0, offsetZ));
                }
            }
        }

        leafCenter = leafCenter.up();
        for(int offsetX = -leafRadius / 2; offsetX <= leafRadius / 2; offsetX++)
        {
            for(int offsetZ = -leafRadius / 2; offsetZ <= leafRadius / 2; offsetZ++)
            {
                this.placeLeafAt(world, leafCenter.add(offsetX, 0, offsetZ));
            }
        }

        if(isTop)
        {
            this.placeLeafAt(world, leafCenter.east(2));
            this.placeLeafAt(world, leafCenter.west(2));
            this.placeLeafAt(world, leafCenter.south(2));
            this.placeLeafAt(world, leafCenter.north(2));
        }
    }
    private void placeLeafAt(World worldIn, BlockPos pos)
    {
        IBlockState state = worldIn.getBlockState(pos);
        if(state.getBlock().isAir(state, worldIn, pos) || state.getBlock().isLeaves(state, worldIn, pos))
        {
            this.setBlockAndNotifyAdequately(worldIn, pos, LEAVES);
        }
    }

    private boolean isValidLocation(World world, BlockPos pos, int height)
    {
        if(pos.getY() < 1 || pos.getY() + height + 1 > 256) return false;

        for(int j = pos.getY(); j <= pos.getY() + 1 + height; ++j)
        {
            int k = 1;
            if(j == pos.getY()) k = 0;
            if(j >= pos.getY() + 1 + height - 2) k = 2;

            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for(int l = pos.getX() - k; l <= pos.getX() + k; ++l)
            {
                for(int i1 = pos.getZ() - k; i1 <= pos.getZ() + k; ++i1)
                {
                    if(j >= 0 && j < 256)
                    {
                        if(!isReplaceable(world, blockpos$mutableblockpos.setPos(l, j, i1))) return false;
                    } else return false;
                }
            }
        }

        BlockPos down = pos.down();
        IBlockState state = world.getBlockState(down);
        boolean isSoil = state.getBlock().canSustainPlant(state, world, down, EnumFacing.UP, ((net.minecraft.block.BlockSapling) Blocks.SAPLING));
        return isSoil && pos.getY() < world.getHeight() - height - 1;
    }
}