package com.fi0x.deepmagic.items.spells.effects.offensive;

import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import com.fi0x.deepmagic.items.spells.modifiers.ISpellModifier;
import net.minecraft.util.math.BlockPos;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;

/*
 * Time gets set to midnight at new-moon
 * Target gets attacked by "shadows"
 */
public class SpEfMidnight implements ISpellEffect
{
    @Override
    public String getName()
    {
        return "effect_midnight";
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
