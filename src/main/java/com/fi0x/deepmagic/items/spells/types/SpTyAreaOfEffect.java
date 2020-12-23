package com.fi0x.deepmagic.items.spells.types;

import com.fi0x.deepmagic.items.spells.ISpellPart;
import net.minecraft.util.math.BlockPos;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;

public class SpTyAreaOfEffect implements ISpellType
{
    private double radius = 1;

    @Override
    public String getName()
    {
        return "type_aoe";
    }
    @Override
    public ISpellType getType()
    {
        return this;
    }

    @Override
    public void execute(ArrayList<ISpellPart> applicableParts, BlockPos castLocation, Entity caster)
    {
        //TODO: Execute spell
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
