package com.fi0x.deepmagic.mana.spells.types;

import com.fi0x.deepmagic.mana.spells.ISpellPart;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public interface ISpellType extends ISpellPart
{
    void execute(ArrayList<ISpellPart> sectionParts, ArrayList<ArrayList<ISpellPart>> remainingSections, BlockPos castLocation, @Nullable EntityLivingBase caster, World world);
}
