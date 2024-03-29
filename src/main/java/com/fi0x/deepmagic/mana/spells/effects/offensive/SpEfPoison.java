package com.fi0x.deepmagic.mana.spells.effects.offensive;

import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.mana.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class SpEfPoison implements ISpellEffect
{
    public static final String NAME = "effect_poison";
    private int power = 1;
    private double seconds = 5;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getDisplayName()
    {
        return "Effect: Poison";
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME + "_attr_";
        ret += power + "_attr_";
        ret += seconds;
        return ret;
    }
    @Override
    public void setAttributesFromString(ArrayList<String> attributes)
    {
        power = Integer.parseInt(attributes.get(0));
        seconds = Double.parseDouble(attributes.get(1));
    }
    @Override
    public ArrayList<ItemStack> getRequiredItems()
    {
        ArrayList<ItemStack> list = new ArrayList<>();

        list.add(new ItemStack(ModItems.MAGIC_CONVERTER));
        list.add(new ItemStack(Items.GLASS_BOTTLE));
        list.add(new ItemStack(Items.SPIDER_EYE));

        return list;
    }

    @Override
    public double[] getCastModifiers()
    {
        return new double[]{30, 0, 0, 0};
    }

    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, BlockPos targetPos, World world)
    {
    }
    @Override
    public void applyEffect(@Nullable EntityLivingBase caster, EntityLivingBase targetEntity)
    {
        targetEntity.addPotionEffect(new PotionEffect(MobEffects.POISON, (int) (20 * seconds), power - 1, false, true));
    }

    @Override
    public void setDuration(double value)
    {
        seconds = value;
    }
    @Override
    public double getDuration()
    {
        return seconds;
    }
    @Override
    public void setPower(int value)
    {
        this.power = value;
    }
    @Override
    public int getPower()
    {
        return power;
    }
}
