package com.fi0x.deepmagic.items.spells.effects.util;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpEfDig implements ISpellEffect
{
    public static final String NAME = "effect_dig";
    private boolean autoSmelt = false;
    private int fortune = 0;
    private boolean silkTouch = false;

    @Override
    public ISpellEffect getEffect()
    {
        return this;
    }
    //TODO: Use one method to apply an effect
    @Override
    public void applyEffect(EntityLivingBase caster, BlockPos targetPos, World world)
    {
    }
    @Override
    public void applyEffect(EntityLivingBase caster, EntityLivingBase targetEntity)
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
