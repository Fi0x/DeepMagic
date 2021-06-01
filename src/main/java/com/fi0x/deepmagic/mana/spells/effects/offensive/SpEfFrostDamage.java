package com.fi0x.deepmagic.mana.spells.effects.offensive;

import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class SpEfFrostDamage implements ISpellEffect
{
    public static final String NAME = "effect_frostdamage";
    private int damage = 1;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Effect: Frost-Damage";
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME + "_attr_";
        ret += damage;
        return ret;
    }
    @Override
    public void setAttributesFromString(ArrayList<String> attributes)
    {
        damage = Integer.parseInt(attributes.get(0));
    }
    @Override
    public ArrayList<ItemStack> getRequiredItems()
    {
        ArrayList<ItemStack> list = new ArrayList<>();

        list.add(new ItemStack(ModItems.MAGIC_CONVERTER));
        list.add(new ItemStack(Blocks.ICE));

        return list;
    }

    @Override
    public double[] getCastModifiers()
    {
        return new double[]{30, 0, 0, 2};
    }

    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, BlockPos targetPos, World world)
    {
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, EntityLivingBase targetEntity)
    {
        targetEntity.attackEntityFrom(DamageSource.GENERIC, damage);//TODO: Change to different damage type
    }

    @Override
    public void setDamage(int value)
    {
        this.damage = value;
    }
    @Override
    public int getDamage()
    {
        return damage;
    }
}
