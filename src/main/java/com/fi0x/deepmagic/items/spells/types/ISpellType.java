package com.fi0x.deepmagic.items.spells.types;

import com.fi0x.deepmagic.items.spells.ISpellPart;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public interface ISpellType extends ISpellPart
{
    void execute(ArrayList<ISpellPart> sectionParts, ArrayList<ArrayList<ISpellPart>> remainingSections, BlockPos castLocation, EntityLivingBase caster, World world);
}
