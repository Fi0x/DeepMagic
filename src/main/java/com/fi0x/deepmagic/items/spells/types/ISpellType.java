package com.fi0x.deepmagic.items.spells.types;

import com.fi0x.deepmagic.items.spells.ISpellPart;
import net.minecraft.util.math.BlockPos;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;

public interface ISpellType extends ISpellPart
{
    void execute(ArrayList<ISpellPart> applicableParts, BlockPos castLocation, Entity caster);
}
