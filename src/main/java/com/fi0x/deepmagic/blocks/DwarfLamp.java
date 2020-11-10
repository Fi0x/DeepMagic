package com.fi0x.deepmagic.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class DwarfLamp extends BlockBase
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    protected static final AxisAlignedBB LAMP_STANDING_AABB = new AxisAlignedBB(0.3125, 0.0D, 0.3125, 0.6875, 0.71875, 0.6875);
    protected static final AxisAlignedBB LAMP_HANGING_AABB = new AxisAlignedBB(0.3125, 0.28125, 0.3125, 0.6875, 1, 0.6875);
    protected static final AxisAlignedBB LAMP_NORTH_AABB = new AxisAlignedBB(0.3125, 0.25, 0.46875, 0.6875, 0.75, 1.0D);
    protected static final AxisAlignedBB LAMP_SOUTH_AABB = new AxisAlignedBB(0.3125, 0.25, 0.0D, 0.6875, 0.75, 0.53125);
    protected static final AxisAlignedBB LAMP_EAST_AABB = new AxisAlignedBB(0.0D, 0.25, 0.3125, 0.53125, 0.75, 0.6875);
    protected static final AxisAlignedBB LAMP_WEST_AABB = new AxisAlignedBB(0.46875, 0.25, 0.3125, 1.0D, 0.75, 0.6875);

    public DwarfLamp(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.METAL);
        setHardness(1.0F);
        setResistance(2.0F);
        setLightLevel(0.9F);
        this.setTickRandomly(true);

        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.DOWN));
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos)
    {
        switch (state.getValue(FACING))
        {
            case NORTH:
                return LAMP_NORTH_AABB;
            case SOUTH:
                return LAMP_SOUTH_AABB;
            case EAST:
                return LAMP_EAST_AABB;
            case WEST:
                return LAMP_WEST_AABB;
            case DOWN:
                return LAMP_HANGING_AABB;
            default:
                return LAMP_STANDING_AABB;
        }
    }
    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(@Nonnull IBlockState blockState, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos)
    {
        return null;
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
        for (EnumFacing enumfacing : FACING.getAllowedValues())
        {
            if (this.canPlaceAt(worldIn, pos, enumfacing))
            {
                return true;
            }
        }

        return false;
    }
    private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing)
    {
        BlockPos blockpos = pos.offset(facing.getOpposite());
        IBlockState iblockstate = worldIn.getBlockState(blockpos);
        Block block = iblockstate.getBlock();
        BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, blockpos, facing);

        if(!facing.equals(EnumFacing.UP) && !facing.equals(EnumFacing.DOWN))
        {
            return !isExceptBlockForAttachWithPiston(block) && blockfaceshape == BlockFaceShape.SOLID;
        } else return canPlaceOn(worldIn, blockpos);
    }
    @Nonnull
    public IBlockState getStateForPlacement(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, @Nonnull EntityLivingBase placer)
    {
        if (this.canPlaceAt(worldIn, pos, facing))
        {
            return this.getDefaultState().withProperty(FACING, facing);
        } else
        {
            for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
            {
                if (this.canPlaceAt(worldIn, pos, enumfacing))
                {
                    return this.getDefaultState().withProperty(FACING, enumfacing);
                }
            }
            if(canPlaceAt(worldIn, pos, EnumFacing.DOWN)) return this.getDefaultState().withProperty(FACING, EnumFacing.DOWN);
            return this.getDefaultState();
        }
    }
    public void onBlockAdded(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state)
    {
        checkForDrop(worldIn, pos, state);
    }
    public void neighborChanged(@Nonnull IBlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Block blockIn, @Nonnull BlockPos fromPos)
    {
        onNeighborChangeInternal(worldIn, pos, state);
    }
    protected void onNeighborChangeInternal(World worldIn, BlockPos pos, IBlockState state)
    {
        if (this.checkForDrop(worldIn, pos, state))
        {
            EnumFacing enumfacing = state.getValue(FACING);
            EnumFacing.Axis enumfacing$axis = enumfacing.getAxis();
            EnumFacing enumfacing1 = enumfacing.getOpposite();
            BlockPos blockpos = pos.offset(enumfacing1);
            boolean flag = false;

            if (enumfacing$axis.isHorizontal() && worldIn.getBlockState(blockpos).getBlockFaceShape(worldIn, blockpos, enumfacing) != BlockFaceShape.SOLID) flag = true;
            else if (enumfacing$axis.isVertical() && !this.canPlaceOn(worldIn, blockpos)) flag = true;

            if (flag)
            {
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }
        }
    }
    protected boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state)
    {
        if (state.getBlock() == this && canPlaceAt(worldIn, pos, state.getValue(FACING)))
        {
            return true;
        }
        else
        {
            if (worldIn.getBlockState(pos).getBlock() == this)
            {
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }
            return false;
        }
    }
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, @Nonnull World worldIn, BlockPos pos, @Nonnull Random rand)
    {
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + 0.6775D;
        double d2 = (double)pos.getZ() + 0.5D;

        EnumFacing facing = stateIn.getValue(FACING);
        if (facing.getAxis().isHorizontal())
        {
            EnumFacing facing1 = facing.getOpposite();
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.1D * (double)facing1.getFrontOffsetX(), d1 + 0.03125D, d2 + 0.1D * (double)facing1.getFrontOffsetZ(), 0.0D, 0.0D, 0.0D);
        } else if(facing.equals(EnumFacing.UP)) worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        else if(facing.equals(EnumFacing.DOWN)) worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1 + 0.0425, d2, 0.0D, 0.0D, 0.0D);
    }
    @Nonnull
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState();

        switch (meta)
        {
            case 1:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST);
                break;
            case 2:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST);
                break;
            case 3:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH);
                break;
            case 4:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH);
                break;
            case 5:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.DOWN);
                break;
            case 6:
            default:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.UP);
        }

        return iblockstate;
    }
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;

        switch (state.getValue(FACING))
        {
            case EAST:
                i = i | 1;
                break;
            case WEST:
                i = i | 2;
                break;
            case SOUTH:
                i = i | 3;
                break;
            case NORTH:
                i = i | 4;
                break;
            case DOWN:
                i = i | 5;
                break;
            case UP:
            default:
                i = i | 6;
        }

        return i;
    }
    @Nonnull
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }
    @Nonnull
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }
    @Nonnull
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }
    @Nonnull
    public BlockFaceShape getBlockFaceShape(@Nonnull IBlockAccess worldIn, @Nonnull IBlockState state, @Nonnull BlockPos pos, @Nonnull EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
    private boolean canPlaceOn(World worldIn, BlockPos pos)
    {
        IBlockState state = worldIn.getBlockState(pos);
        return state.getBlock().canPlaceTorchOnTop(state, worldIn, pos);
    }
}