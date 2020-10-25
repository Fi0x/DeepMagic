package com.fi0x.deepmagic.blocks;

import com.fi0x.deepmagic.entities.tileentity.BlockTileEntity;
import com.fi0x.deepmagic.entities.tileentity.TileEntitySpellStone;
import com.fi0x.deepmagic.items.mana.CustomSpell;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SpellStone extends BlockTileEntity<TileEntitySpellStone>
{
    public SpellStone(String name, Material material)
    {
        super(name, material);
        setSoundType(SoundType.METAL);
        setHardness(5.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 2);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(worldIn.isRemote) return false;
        ItemStack stack = playerIn.getHeldItem(hand);
        Item item = stack.getItem();
        TileEntitySpellStone tile = getTileEntity(worldIn, pos);

        if(item instanceof CustomSpell)
        {
            NBTTagCompound compound;
            if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
            compound = stack.getTagCompound();
            assert compound != null;

            playerIn.sendMessage(new TextComponentString(TextFormatting.YELLOW + "Spell Stone used"));
            compound.setInteger("manaCosts", 10);
            compound.setInteger("tier", 0);
            compound.setInteger("range", 10);
            compound.setInteger("target", 0);
            compound.setInteger("radius", 0);
            if(compound.getInteger("damage") < tile.getAttackDmg())
            {
                compound.setInteger("damage", tile.getAttackDmg());
                tile.resetAttackDmg();
            }
            compound.setBoolean("environmentalDamage", false);
            compound.setBoolean("explosion", false);
            compound.setInteger("heal", 5);
            compound.setInteger("time", 10);
            compound.setBoolean("weather", true);
            return true;
        }

        if(item instanceof ItemSword)
        {
            if(((ItemSword) item).getToolMaterialName().equals("WOOD"))
            {
                if(tile.getAttackDmg() < 1)
                {
                    playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You set the damage value to 1"));
                    tile.setAttackDmg(1);
                }
                else return false;
            }
            if(((ItemSword) item).getToolMaterialName().equals("STONE"))
            {
                if(tile.getAttackDmg() < 2)
                {
                    playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You set the damage value to 2"));
                    tile.setAttackDmg(2);
                }
                else return false;
            }
            if(((ItemSword) item).getToolMaterialName().equals("IRON"))
            {
                if(tile.getAttackDmg() < 3)
                {
                    playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You set the damage value to 3"));
                    tile.setAttackDmg(3);
                }
                else return false;
            }
            if(((ItemSword) item).getToolMaterialName().equals("GOLD"))
            {
                if(tile.getAttackDmg() < 4)
                {
                    playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You set the damage value to 4"));
                    tile.setAttackDmg(4);
                }
                else return false;
            }
            if(((ItemSword) item).getToolMaterialName().equals("DIAMOND"))
            {
                if(tile.getAttackDmg() < 5)
                {
                    playerIn.sendMessage(new TextComponentString(TextFormatting.GREEN + "You set the damage value to 5"));
                    tile.setAttackDmg(5);
                }
                else return false;
            }
            stack.setCount(stack.getCount() - 1);
            return true;
        }
        return false;
    }
    @Override
    public Class<TileEntitySpellStone> getTileEntityClass()
    {
        return TileEntitySpellStone.class;
    }
    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntitySpellStone();
    }
}