package com.fi0x.deepmagic.blocks.insanity;

import com.fi0x.deepmagic.blocks.BlockBase;
import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nonnull;
import java.util.Random;

public class InsanityGrass extends BlockBase implements IGrowable
{
    
    public InsanityGrass(String name, Material material)
    {
        super(name, material);
        setTickRandomly(true);
        setSoundType(SoundType.GROUND);
        setHardness(0.7F);
		setHarvestLevel("shovel", 2);
    }
    
    public void updateTick(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand)
    {
        if (!worldIn.isRemote)
        {
            if (!worldIn.isAreaLoaded(pos, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2)
            {
                worldIn.setBlockState(pos, ModBlocks.INSANITY_DIRT.getDefaultState());
            }
            else
            {
                if (worldIn.getLightFromNeighbors(pos.up()) >= 9)
                {
                    for (int i = 0; i < 4; ++i)
                    {
                        BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

                        if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos))
                        {
                            return;
                        }

                        IBlockState iblockstate = worldIn.getBlockState(blockpos.up());
                        IBlockState iblockstate1 = worldIn.getBlockState(blockpos);

                        if (iblockstate1.getBlock() == ModBlocks.INSANITY_DIRT && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate.getLightOpacity(worldIn, pos.up()) <= 2)
                        {
                            worldIn.setBlockState(blockpos, ModBlocks.INSANITY_GRASS.getDefaultState());
                        }
                    }
                }
            }
        }
    }
    public boolean canGrow(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, boolean isClient)
    {
        return true;
    }
    public boolean canUseBonemeal(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull IBlockState state)
    {
        return false;
    }
    public void grow(@Nonnull World worldIn, @Nonnull Random rand, BlockPos pos, @Nonnull IBlockState state)
    {
        BlockPos blockpos = pos.up();

        for (int i = 0; i < 128; ++i)
        {
            BlockPos blockpos1 = blockpos;
            int j = 0;

            while (true)
            {
                if (j >= i / 16)
                {
                    break;
                }

                blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);

                if (worldIn.getBlockState(blockpos1.down()).getBlock() != ModBlocks.INSANITY_GRASS || worldIn.getBlockState(blockpos1).isNormalCube())
                {
                    break;
                }
                ++j;
            }
        }
    }
    public int getMetaFromState(@Nonnull IBlockState state)
    {
        return 0;
    }
    
    @Override
    public boolean canSustainPlant(@Nonnull IBlockState state, @Nonnull IBlockAccess world, BlockPos pos, @Nonnull EnumFacing direction, IPlantable plantable)
    {
        net.minecraftforge.common.EnumPlantType plantType = plantable.getPlantType(world, pos.offset(direction));

        if (plantable instanceof BlockBush) return true;

        switch (plantType)
        {
            case Cave:   return state.isSideSolid(world, pos, EnumFacing.UP);
            case Plains: return true;
            case Water:  return state.getMaterial() == Material.WATER && state.getValue(BlockLiquid.LEVEL) == 0;
            case Beach:
                return (world.getBlockState(pos.east()).getMaterial() == Material.WATER || world.getBlockState(pos.west()).getMaterial() == Material.WATER || world.getBlockState(pos.north()).getMaterial() == Material.WATER || world.getBlockState(pos.south()).getMaterial() == Material.WATER);
            default:
            	return false;
        }
    }
    
    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune)
    {
		return Item.getItemFromBlock(ModBlocks.INSANITY_DIRT);
    }
	@Override
	public int quantityDropped(Random random)
	{
		return random.nextInt(3);
	}
}