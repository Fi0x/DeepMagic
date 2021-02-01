package com.fi0x.deepmagic.blocks.rituals;

import com.fi0x.deepmagic.blocks.rituals.tile.TileEntityRitualTime;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RitualTime extends RitualStone implements ITileEntityProvider
{
    public RitualTime(String name, Material material)
    {
        super(name, material);
    }

    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);

        if(!worldIn.isRemote)
        {
            ItemStack stack = playerIn.getHeldItem(hand);
            Item item = stack.getItem();

            if(item.getUnlocalizedName().equals("item.diamond"))
            {
                TileEntityRitualTime tile = (TileEntityRitualTime) worldIn.getTileEntity(pos);
                assert tile != null;
                double newTime = tile.addTime(1000);
                playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Ritual time set to " + newTime));
                stack.shrink(1);
            } else if(item.getUnlocalizedName().equals("item.deep_crystal"))
            {
                TileEntityRitualTime tile = (TileEntityRitualTime) worldIn.getTileEntity(pos);
                assert tile != null;
                double newTime = tile.addTime(10000);
                playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Ritual time set to " + newTime));
                stack.shrink(1);
            } else if(item.getUnlocalizedName().equals("item.iron_ingot"))
            {
                TileEntityRitualTime tile = (TileEntityRitualTime) worldIn.getTileEntity(pos);
                assert tile != null;
                double newTime = tile.addTime(100);
                playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Ritual time set to " + newTime));
                stack.shrink(1);
            }
        }

        return true;
    }
    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta)
    {
        return new TileEntityRitualTime();
    }
}