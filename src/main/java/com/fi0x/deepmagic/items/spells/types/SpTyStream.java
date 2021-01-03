package com.fi0x.deepmagic.items.spells.types;

import com.fi0x.deepmagic.items.spells.ISpellPart;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

/*
 * Constant stream of the effect
 * Like the beam, but not as focused
 */
public class SpTyStream implements ISpellType
{
    private double range = 4;
    private double radius = 1;

    @Override
    public String getName()
    {
        return "type_stream";
    }
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
    @Override
    public void setRadius(double value)
    {
        radius = value;
    }
    @Override
    public double getRadius()
    {
        return radius;
    }
}
