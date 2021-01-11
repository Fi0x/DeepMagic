package com.fi0x.deepmagic.items.spells.types;

import com.fi0x.deepmagic.items.spells.CastHelper;
import com.fi0x.deepmagic.items.spells.ISpellPart;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class SpTyIterate implements ISpellType
{
    public static final String NAME = "type_iterate";
    private int iterations = 4;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME + "_attr_";
        ret += iterations;
        return ret;
    }
    @Override
    public void setAttributesFromString(String[] attributes)
    {
        iterations = Integer.parseInt(attributes[0]);
    }
    @Override
    public ISpellType getType()
    {
        return this;
    }

    @Override
    public void execute(ArrayList<ISpellPart> applicableParts, ArrayList<ArrayList<ISpellPart>> remainingSections, BlockPos castLocation, @Nullable EntityLivingBase caster, World world)
    {
        applicableParts.remove(0);

        boolean executed = false;

        while(!applicableParts.isEmpty() && !(applicableParts.get(0) instanceof ISpellType))
        {
            applicableParts.remove(0);
        }
        if(!applicableParts.isEmpty())
        {
            ((ISpellType) applicableParts.get(0)).execute(applicableParts, remainingSections, castLocation, caster, world);
            executed = true;
        }

        if(!executed)
        {
            new CastHelper().findAndCastNextSpellType(remainingSections, castLocation, caster, world);
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
