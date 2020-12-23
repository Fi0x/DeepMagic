package com.fi0x.deepmagic.items.spells.types;

import com.fi0x.deepmagic.items.spells.ISpellPart;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public interface ISpellType extends ISpellPart
{
    void execute(ArrayList<ISpellPart> sectionParts, BlockPos castLocation, EntityLivingBase caster);
}
