package com.fi0x.deepmagic.blocks;

import com.fi0x.deepmagic.items.mana.CustomSpell;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class SpellStone extends BlockBase
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
        if(stack.getItem() instanceof CustomSpell)
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
            compound.setInteger("damage", 0);
            compound.setBoolean("environmentalDamage", false);
            compound.setBoolean("explosion", false);
            compound.setInteger("heal", 5);
            compound.setInteger("time", 10);
            compound.setBoolean("weather", true);
            return true;
        }
        return false;
    }
}