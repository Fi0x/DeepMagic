package com.fi0x.deepmagic.init;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class BlockColors implements IBlockColor
{
    public static final IBlockColor INSTANCE = new BlockColors();

    @Override
    public int colorMultiplier(@Nonnull IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex)
    {
        assert worldIn != null;
        return worldIn.getBiome(pos).getFoliageColorAtPos(pos);
    }

    public static void registerColors()
    {
        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(INSTANCE, ModBlocks.INSANITY_LEAVES);
    }
}