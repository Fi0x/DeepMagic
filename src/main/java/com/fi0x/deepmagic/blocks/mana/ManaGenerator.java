package com.fi0x.deepmagic.blocks.mana;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.blocks.BlockBase;
import com.fi0x.deepmagic.entities.tileentity.TileEntityManaGenerator;
import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class ManaGenerator extends BlockBase implements ITileEntityProvider
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool RUNNING = PropertyBool.create("running");

    public ManaGenerator(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.STONE);
        setHardness(5.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 0);

        setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(RUNNING, false));
    }

    @Override
    public boolean onBlockActivated(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            Main.proxy.openManaGeneratorGui();
        }
        return true;
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.MANA_GENERATOR);
    }
    @Override
    public void breakBlock(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state)
    {
        TileEntityManaGenerator te = (TileEntityManaGenerator) worldIn.getTileEntity(pos);
        InventoryHelper.dropInventoryItems(worldIn, pos, te);
        super.breakBlock(worldIn, pos, state);
    }
    @Nonnull
    @Override
    public ItemStack getItem(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state)
    {
        return new ItemStack(ModBlocks.MANA_GENERATOR);
    }
    @Override
    public void onBlockAdded(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state)
    {
        if(!worldIn.isRemote)
        {
            IBlockState north = worldIn.getBlockState(pos.north());
            IBlockState south = worldIn.getBlockState(pos.south());
            IBlockState east = worldIn.getBlockState(pos.east());
            IBlockState west = worldIn.getBlockState(pos.west());
            EnumFacing face = state.getValue(FACING);

            if(face == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) face = EnumFacing.SOUTH;
            else if(face == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock()) face = EnumFacing.NORTH;
            else if(face == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock()) face = EnumFacing.WEST;
            else if(face == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock()) face = EnumFacing.EAST;
            worldIn.setBlockState(pos, state.withProperty(FACING, face), 2);
        }
    }
    @Nonnull
    @Override
    public IBlockState getStateForPlacement(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, @Nonnull EntityLivingBase placer, @Nonnull EnumHand hand)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }
    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {RUNNING, FACING});
    }
    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta)
    {
        return new TileEntityManaGenerator();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing facing = EnumFacing.getFront(meta);
        if(facing.getAxis() == EnumFacing.Axis.Y) facing = EnumFacing.NORTH;
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }

    public static void setState(boolean active, World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        TileEntity te = world.getTileEntity(pos);
        if(active) world.setBlockState(pos, ModBlocks.MANA_GENERATOR.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(RUNNING, true), 3);
        else world.setBlockState(pos, ModBlocks.MANA_GENERATOR.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(RUNNING, false), 3);

        if(te != null)
        {
            te.validate();
            world.setTileEntity(pos, te);
        }
    }
}