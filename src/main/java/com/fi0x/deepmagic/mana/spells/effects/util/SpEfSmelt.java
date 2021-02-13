package com.fi0x.deepmagic.mana.spells.effects.util;

import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.effects.ISpellEffect;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class SpEfSmelt implements ISpellEffect
{
    public static final String NAME = "effect_smelt";
    private int harvestLevel = 0;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Effect: Smelt";
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME + "_attr_";
        ret += harvestLevel;
        return ret;
    }
    @Override
    public void setAttributesFromString(ArrayList<String> attributes)
    {
        harvestLevel = Integer.parseInt(attributes.get(0));
    }
    @Override
    public ArrayList<ItemStack> getRequiredItems()
    {
        ArrayList<ItemStack> list = new ArrayList<>();

        list.add(new ItemStack(ModItems.MAGIC_CONVERTER));
        list.add(new ItemStack(Items.IRON_PICKAXE));
        list.add(new ItemStack(Items.FLINT_AND_STEEL));

        return list;
    }

    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, BlockPos targetPos, World world)
    {
        Block oldBlock = world.getBlockState(targetPos).getBlock();
        if(oldBlock.getHarvestLevel(world.getBlockState(targetPos)) > harvestLevel) return;

        ItemStack targetBlockStack = new ItemStack(oldBlock);
        ItemStack resultBlockStack = FurnaceRecipes.instance().getSmeltingResult(targetBlockStack);

        if(!resultBlockStack.isEmpty())
        {
            Block newBlock = Block.getBlockFromItem(resultBlockStack.getItem());
            if(newBlock != Blocks.AIR)
            {
                world.setBlockState(targetPos, newBlock.getDefaultState());
            }
        }
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, EntityLivingBase targetEntity)
    {
    }

    @Override
    public void setPower(int value)
    {
        harvestLevel = value;
    }
    @Override
    public int getPower()
    {
        return harvestLevel;
    }
}
