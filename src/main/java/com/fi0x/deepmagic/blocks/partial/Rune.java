package com.fi0x.deepmagic.blocks.partial;

import com.fi0x.deepmagic.blocks.BlockBase;
import com.fi0x.deepmagic.blocks.tileentity.TileEntityRune;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class Rune extends BlockBase implements ITileEntityProvider
{
    protected static final AxisAlignedBB RUNE_AABB = new AxisAlignedBB(0., 0.0D, 0., 1, 0.0625, 1);

    public Rune(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.STONE);
        setHardness(1.0F);
        setResistance(1.0F);
        setTickRandomly(true);
    }

    @Override
    public void onEntityCollidedWithBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Entity entityIn)
    {
        TileEntityRune te = (TileEntityRune) worldIn.getTileEntity(pos);
        assert te != null;
        if(entityIn instanceof EntityLivingBase) te.executeSpell((EntityLivingBase) entityIn);
    }
    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune)
    {
        return Items.AIR;
    }
    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta)
    {
        return new TileEntityRune();
    }
    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos)
    {
        return RUNE_AABB;
    }
    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(@Nonnull IBlockState blockState, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos)
    {
        return RUNE_AABB;
    }
    @Override
    public boolean isPassable(@Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos)
    {
        return true;
    }
    @Override
    public boolean isOpaqueCube(@Nonnull IBlockState state)
    {
        return false;
    }
    @Override
    public boolean isFullCube(@Nonnull IBlockState state)
    {
        return false;
    }
    public boolean canPlaceBlockAt(@Nonnull World worldIn, @Nonnull BlockPos pos)
    {
        IBlockState state = worldIn.getBlockState(pos.down());
        return state.getBlock().canPlaceTorchOnTop(state, worldIn, pos);
    }
    @Override
    public void neighborChanged(@Nonnull IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Block blockIn, @Nonnull BlockPos fromPos)
    {
        if(!canPlaceBlockAt(worldIn, pos))
        {
            worldIn.setBlockToAir(pos);
            worldIn.newExplosion(null, pos.getX() + 0.5, pos.getZ() + 0.5, pos.getY() + 0.5, 2, false, true);
        }
    }
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(@Nonnull IBlockState stateIn, @Nonnull World worldIn, BlockPos pos, @Nonnull Random rand)
    {
        double x = (double) pos.getX() + rand.nextDouble();
        double y = (double) pos.getY() + 0.0625;
        double z = (double) pos.getZ() + rand.nextDouble();

        switch(rand.nextInt(2))
        {
            case 0:
                worldIn.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, x, y + 0.1875, z, 0, 0, 0);
                break;
            case 1:
                worldIn.spawnParticle(EnumParticleTypes.TOWN_AURA, x, y, z, 0, 0, 0);
                break;
        }
    }
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    @Nonnull
    public BlockFaceShape getBlockFaceShape(@Nonnull IBlockAccess worldIn, @Nonnull IBlockState state, @Nonnull BlockPos pos, @Nonnull EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
