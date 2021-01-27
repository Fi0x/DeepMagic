package com.fi0x.deepmagic.blocks;

import com.fi0x.deepmagic.blocks.tileentity.TileEntityDwarfBaseMarker;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class DwarfBaseMarker extends BlockBase implements ITileEntityProvider
{
    public DwarfBaseMarker(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.STONE);
        setHardness(10.0F);
        setResistance(10.0F);
        setHarvestLevel("pickaxe", 2);
        setLightLevel(0.5F);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(@Nonnull IBlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand)
    {
        if(rand.nextInt(15) + 1 > ConfigHandler.dwarfBaseMarkerParticles) return;

        if(worldIn.getBlockState(pos.up()).getCollisionBoundingBox(worldIn, pos.up()) == null)
        {
            double d0 = (double) pos.getX() + Math.random();
            double d1 = (double) pos.getY() + 1;
            double d2 = (double) pos.getZ() + Math.random();

            worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.DWARF_BASE_MARKER);
    }
    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta)
    {
        return new TileEntityDwarfBaseMarker();
    }
}