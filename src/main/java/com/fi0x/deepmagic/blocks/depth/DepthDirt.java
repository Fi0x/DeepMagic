package com.fi0x.deepmagic.blocks.depth;

import com.fi0x.deepmagic.blocks.BlockBase;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nonnull;
import java.util.Random;

public class DepthDirt extends BlockBase
{

    public DepthDirt(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.GROUND);
        setHardness(1.6F);
        setHarvestLevel("shovel", 2);
    }

    @Override
    public int quantityDropped(Random random)
    {
        return random.nextInt(4);
    }
    @Override
    public boolean canSustainPlant(@Nonnull IBlockState state, @Nonnull IBlockAccess world, BlockPos pos, @Nonnull EnumFacing direction, IPlantable plantable)
    {
        EnumPlantType plantType = plantable.getPlantType(world, pos.offset(direction));

        if(plantable instanceof BlockBush) return true;

        switch(plantType)
        {
            case Cave:
                return state.isSideSolid(world, pos, EnumFacing.UP);
            case Water:
                return state.getMaterial() == Material.WATER && state.getValue(BlockLiquid.LEVEL) == 0;
            case Plains:
            case Beach:
                return true;
            default:
                return false;
        }
    }
}