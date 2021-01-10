package com.fi0x.deepmagic.blocks.mana;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.blocks.BlockBase;
import com.fi0x.deepmagic.blocks.tileentity.TileEntityManaGeneratorInsanity;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.items.mana.ManaLinker;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class ManaGeneratorInsanity extends BlockBase implements ITileEntityProvider
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool RUNNING = PropertyBool.create("running");

    public ManaGeneratorInsanity(String name, Material material)
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
            ItemStack stack = playerIn.getHeldItem(hand);
            Item item = stack.getItem();

            if(item instanceof ManaLinker)
            {
                NBTTagCompound compound;
                if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
                compound = stack.getTagCompound();
                assert compound != null;

                TileEntityManaGeneratorInsanity te = (TileEntityManaGeneratorInsanity) worldIn.getTileEntity(pos);
                assert te != null;

                if(compound.hasKey("linked") && compound.getBoolean("linked"))
                {
                    int x = compound.getInteger("x");
                    int y = compound.getInteger("y");
                    int z = compound.getInteger("z");
                    te.setManaTargetPos(new BlockPos(x, y, z));
                    playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Linked to " + x + ", " + y + ", " + z));
                } else if(compound.hasKey("linked"))
                {
                    te.setManaTargetPos(null);
                    playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Unlinked Generator"));
                }
            }
            else playerIn.openGui(Main.instance, ConfigHandler.guiManaGeneratorInsanityID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.MANA_GENERATOR_INSANITY);
    }
    @Override
    public void breakBlock(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state)
    {
        TileEntityManaGeneratorInsanity te = (TileEntityManaGeneratorInsanity) worldIn.getTileEntity(pos);
        assert te != null;
        InventoryHelper.dropInventoryItems(worldIn, pos, te);
        super.breakBlock(worldIn, pos, state);
    }
    @Nonnull
    @Override
    public ItemStack getItem(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state)
    {
        return new ItemStack(ModBlocks.MANA_GENERATOR_INSANITY);
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
    public void onBlockPlacedBy(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, EntityLivingBase placer, @Nonnull ItemStack stack)
    {
        worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }
    @Nonnull
    @Override
    public EnumBlockRenderType getRenderType(@Nonnull IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
    @Nonnull
    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }
    @Nonnull
    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }
    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, RUNNING, FACING);
    }
    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta)
    {
        return new TileEntityManaGeneratorInsanity();
    }

    @Nonnull
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
        if(active) world.setBlockState(pos, ModBlocks.MANA_GENERATOR_INSANITY.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(RUNNING, true), 3);
        else world.setBlockState(pos, ModBlocks.MANA_GENERATOR_INSANITY.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(RUNNING, false), 3);

        if(te != null)
        {
            te.validate();
            world.setTileEntity(pos, te);
        }
    }
}