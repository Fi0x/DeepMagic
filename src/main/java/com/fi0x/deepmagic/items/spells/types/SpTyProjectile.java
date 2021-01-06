package com.fi0x.deepmagic.items.spells.types;

import com.fi0x.deepmagic.items.spells.ISpellPart;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class SpTyProjectile implements ISpellType
{
    public static final String NAME = "type_projectile";
    private double range = 16;

    @Override
    public ISpellType getType()
    {
        return this;
    }

    @Override
    public void execute(ArrayList<ISpellPart> applicableParts, ArrayList<ArrayList<ISpellPart>> remainingSections, BlockPos castLocation, EntityLivingBase caster)
    {
        applicableParts.remove(0);
        //TODO: Execute spell
    }

    @Override
    public void setRange(double value)
    {
        range = value;
    }
    @Override
    public double getRange()
    {
        return range;
    }
}
