package com.fi0x.deepmagic.blocks.worldcontroller;

import com.fi0x.deepmagic.blocks.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class WeatherController extends BlockBase
{
    public WeatherController(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.METAL);
        setHardness(3.0F);
        setHarvestLevel("pickaxe", 1);
    }

    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        worldIn.getWorldInfo().setRaining(!worldIn.getWorldInfo().isRaining());
        return true;
    }
}