package com.fi0x.deepmagic.items.spells.effects.util;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class SpEfCookie implements ISpellEffect
{
    public static final String NAME = "effect_cookie";

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME;
        return ret;
    }
    @Override
    public ISpellEffect getEffect()
    {
        return this;
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, BlockPos targetPos, World world)
    {
        int y = 1;
        for(; y < 3; y++)
        {
            if(!world.isAirBlock(targetPos.add(0, y, 0)))
            {
                y--;
                break;
            }
        }
        EntityItem cookie = new EntityItem(world, targetPos.getX() + Math.random(), targetPos.getY() + y, targetPos.getZ() + Math.random(), new ItemStack(Items.COOKIE, 1));
        world.spawnEntity(cookie);
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, EntityLivingBase targetEntity)
    {
    }
}
