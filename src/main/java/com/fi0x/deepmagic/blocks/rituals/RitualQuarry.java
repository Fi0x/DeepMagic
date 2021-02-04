package com.fi0x.deepmagic.blocks.rituals;

import com.fi0x.deepmagic.blocks.rituals.tile.TileEntityRitualQuarry;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemCompass;
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

public class RitualQuarry extends RitualStone implements ITileEntityProvider
{
    public RitualQuarry(String name, Material material)
    {
        super(name, material);
    }

    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            ItemStack stack = playerIn.getHeldItem(hand);
            Item item = stack.getItem();

            if(item instanceof ItemCompass)
            {
                TileEntityRitualQuarry te = (TileEntityRitualQuarry) worldIn.getTileEntity(pos);
                assert te != null;
                EnumFacing dir = te.nextDirection();
                playerIn.sendMessage(new TextComponentString(TextFormatting.WHITE + "Travel direction of quarry changed to " + dir));
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta)
    {
        return new TileEntityRitualQuarry();
    }
}