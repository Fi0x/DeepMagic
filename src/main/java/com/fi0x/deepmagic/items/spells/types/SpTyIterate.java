package com.fi0x.deepmagic.items.spells.types;

import com.fi0x.deepmagic.items.spells.CastHelper;
import com.fi0x.deepmagic.items.spells.ISpellPart;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class SpTyIterate implements ISpellType
{
    private int iterations = 4;

    @Override
    public String getName()
    {
        return "type_iterate";
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

        boolean executed = false;
        //TODO: Execute spell

        if(!executed)
        {
            new CastHelper().findAndCastNextSpellType(remainingSections, caster);
        }
    }

    @Override
    public void setDuration(double value)
    {
        iterations = (int) value;
    }
    @Override
    public double getDuration()
    {
        return iterations;
    }
}
