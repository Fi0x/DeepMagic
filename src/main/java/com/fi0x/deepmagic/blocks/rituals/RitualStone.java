package com.fi0x.deepmagic.blocks.rituals;

import com.fi0x.deepmagic.blocks.BlockBase;
import com.fi0x.deepmagic.blocks.rituals.tile.TileEntityRitualStone;
import com.fi0x.deepmagic.items.mana.ManaLinker;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RitualStone extends BlockBase
{
    public RitualStone(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.STONE);
        setHardness(5.0F);
        setHarvestLevel("pickaxe", 1);
    }

    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
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

                if(compound.hasKey("x")) playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "This block can't send mana"));
                else
                {
                    compound.setInteger("x", pos.getX());
                    compound.setInteger("y", pos.getY());
                    compound.setInteger("z", pos.getZ());
                    playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Location stored"));
                }
            } else if(stack.isEmpty())
            {
                TileEntityRitualStone te = (TileEntityRitualStone) worldIn.getTileEntity(pos);
                assert te != null;
                if(te.changeRedstoneMode()) playerIn.sendMessage(new TextComponentString(TextFormatting.WHITE + "Redstone mode activated"));
                else playerIn.sendMessage(new TextComponentString(TextFormatting.WHITE + "Redstone mode deactivated"));
            }
        }
        return true;
    }

    @Override
    public boolean canConnectRedstone(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nullable EnumFacing side)
    {
        return side != null;
    }
}