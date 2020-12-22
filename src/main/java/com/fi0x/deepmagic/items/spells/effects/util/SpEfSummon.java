package com.fi0x.deepmagic.items.spells.effects.util;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import com.fi0x.deepmagic.items.spells.modifiers.ISpellModifier;
import net.minecraft.util.math.BlockPos;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;

public class SpEfSummon implements ISpellEffect
{
    @Override
    public String getName()
    {
        return "effect_summon";
    }
    @Override
    public ISpellEffect getEffect()
    {
        return this;
    }
    //TODO: Use one method to apply an effect
    @Override
    public void applyEffect(BlockPos targetPos, ArrayList<ISpellModifier> modifiers)
    {
    }
    @Override
    public void applyEffect(Entity targetEntity, ArrayList<ISpellModifier> modifiers)
    {
    }
}
