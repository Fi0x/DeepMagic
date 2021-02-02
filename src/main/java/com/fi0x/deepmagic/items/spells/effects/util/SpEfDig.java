package com.fi0x.deepmagic.items.spells.effects.util;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class SpEfDig implements ISpellEffect
{
    public static final String NAME = "effect_dig";
    private int harvestLevel = 0;
    private boolean autoSmelt = false;
    private int fortune = 0;
    private boolean silkTouch = false;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME + "_attr_";
        ret += harvestLevel + "_attr_";
        ret += autoSmelt + "_attr_";
        ret += fortune + "_attr_";
        ret += silkTouch;
        return ret;
    }
    @Override
    public void setAttributesFromString(ArrayList<String> attributes)
    {
        harvestLevel = Integer.parseInt(attributes.get(0));
        autoSmelt = Boolean.parseBoolean(attributes.get(1));
        fortune = Integer.parseInt(attributes.get(2));
        silkTouch = Boolean.parseBoolean(attributes.get(3));
    }
    @Override
    public ISpellEffect getEffect()
    {
        return this;
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, BlockPos targetPos, World world)
    {
        IBlockState state = world.getBlockState(targetPos);
        int level = state.getBlock().getHarvestLevel(state);
        if(harvestLevel >= level)
        {
            ItemStack drop = ItemStack.EMPTY;
            if(silkTouch)
            {
                /*
                TODO: Find out which tool is required
                 Create itemstack with that tool
                 Enchant tool with silk touch
                 Harvest block
                 check for autoSmelt
                 return
                 */
            } else
            {
                Item item = state.getBlock().getItemDropped(state, world.rand, fortune);
                int amount = state.getBlock().quantityDropped(state, fortune, world.rand);
                drop = new ItemStack(item, amount);
            }

            if(autoSmelt)
            {
                ItemStack result = FurnaceRecipes.instance().getSmeltingResult(drop);
                if(result != ItemStack.EMPTY) drop = result;
            }

            world.setBlockToAir(targetPos);
            EntityItem entity = new EntityItem(world, targetPos.getX(), targetPos.getY(), targetPos.getZ(), drop);
            world.spawnEntity(entity);
        }
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, EntityLivingBase targetEntity)
    {
    }

    @Override
    public void setAutoSmelt(boolean state)
    {
        autoSmelt = state;
    }
    @Override
    public boolean hasAutoSmelt()
    {
        return autoSmelt;
    }
    @Override
    public void setFortune(int level)
    {
        fortune = level;
    }
    @Override
    public int getFortuneLvl()
    {
        return fortune;
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
    @Override
    public void setSilkTouch(boolean state)
    {
        silkTouch = state;
    }
    @Override
    public boolean hasSilkTouch()
    {
        return silkTouch;
    }
}
