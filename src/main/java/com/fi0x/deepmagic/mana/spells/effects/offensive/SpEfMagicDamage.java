package com.fi0x.deepmagic.mana.spells.effects.offensive;

import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class SpEfMagicDamage implements ISpellEffect
{
    public static final String NAME = "effect_magicdamage";
    private int damage = 1;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Effect: Magic-Damage";
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
        list.add(new ItemStack(ModItems.MAGIC_POWDER));
        list.add(new ItemStack(Items.IRON_SWORD));

        return list;
    }

    @Override
    public double[] getCastModifiers()
    {
        return new double[]{40, 0, 0, 0};
    }

    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, BlockPos targetPos, World world)
    {
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, EntityLivingBase targetEntity)
    {
        targetEntity.attackEntityFrom(DamageSource.MAGIC, damage);
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
