package com.fi0x.deepmagic.blocks.depth;

import com.fi0x.deepmagic.blocks.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.Random;

public class DepthGlowstone extends BlockBase
{
    public DepthGlowstone(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.GLASS);
        setHardness(1.4F);
        setResistance(1.4F);
        setHarvestLevel("pickaxe", 1);
        setLightLevel(1F);
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune)
    {
        return Items.GLOWSTONE_DUST;
    }
    @Override
    public int quantityDropped(@Nonnull Random random)
    {
        return 6;
    }
}