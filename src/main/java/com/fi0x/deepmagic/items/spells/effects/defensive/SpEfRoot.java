package com.fi0x.deepmagic.items.spells.effects.defensive;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import com.fi0x.deepmagic.items.spells.modifiers.ISpellModifier;
import net.minecraft.util.math.BlockPos;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;

/*
 * Target can not move any more
 */
public class SpEfRoot implements ISpellEffect
{
    @Override
    public String getName()
    {
        return "effect_root";
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
