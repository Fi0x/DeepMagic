package com.fi0x.deepmagic.blocks;

import com.fi0x.deepmagic.entities.tileentity.BlockTileEntity;
import com.fi0x.deepmagic.entities.tileentity.TileEntityManaAltar;
import com.fi0x.deepmagic.items.mana.ManaChargedSpell;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ManaAltar extends BlockTileEntity<TileEntityManaAltar>
{
    public ManaAltar(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.STONE);
        setHardness(5.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 2);
    }

    @Override
    public boolean onBlockActivated(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isRemote) return false;
        ItemStack stack = playerIn.getHeldItem(hand);
        Item item = stack.getItem();
        TileEntityManaAltar tile = getTileEntity(worldIn, pos);

        if(item instanceof ManaChargedSpell)
        {
            NBTTagCompound compound;
            if(!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
            compound = stack.getTagCompound();
            int manaAmount = (int) tile.getStoredMana();

            assert compound != null;
            compound.setInteger("manaCharge", compound.getInteger("manaCharge") + manaAmount);
            tile.removeManaFromStorage(manaAmount);
            playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "Spell charged"));
        } else
        {
            if(tile.addManaToStorage(100)) playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "ManaStorage of Altar has been increased to " + tile.getStoredMana()));
            else playerIn.sendMessage(new TextComponentString(TextFormatting.RED + "ManaStorage of Altar seems to be full"));
        }
        return true;
    }
    @Override
    public Class<TileEntityManaAltar> getTileEntityClass()
    {
        return TileEntityManaAltar.class;
    }
    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityManaAltar();
    }
}