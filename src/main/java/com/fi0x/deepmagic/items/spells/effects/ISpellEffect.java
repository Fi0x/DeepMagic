package com.fi0x.deepmagic.items.spells.effects;

import com.fi0x.deepmagic.items.spells.ISpellPart;
import com.fi0x.deepmagic.items.spells.modifiers.ISpellModifier;
import net.minecraft.util.math.BlockPos;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;

public interface ISpellEffect extends ISpellPart
{
    default void applyEffect(BlockPos targetPos, ArrayList<ISpellModifier> modifiers)
    {
    }
    default void applyEffect(Entity targetEntity, ArrayList<ISpellModifier> modifiers)
    {
    }
}
