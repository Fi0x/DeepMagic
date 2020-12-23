package com.fi0x.deepmagic.items.spells;

import com.fi0x.deepmagic.items.spells.types.ISpellType;
import net.minecraft.entity.EntityLivingBase;

import java.util.ArrayList;

public class CastHelper
{
    public boolean findAndCastNextSpellType(ArrayList<ArrayList<ISpellPart>> remainingSections, EntityLivingBase caster)
    {
        boolean executed = false;
        for(int i = 0; i < remainingSections.size(); i++)
        {
            for(int j = 0; j < remainingSections.get(i).size(); j++)
            {
                if(remainingSections.get(i).get(j) instanceof ISpellType)
                {
                    ArrayList<ArrayList<ISpellPart>> nextSectionSet = new ArrayList<>();
                    for(int k = i + 1; k < remainingSections.size(); k++)
                    {
                        nextSectionSet.add(remainingSections.get(k));
                    }

                    ((ISpellType) remainingSections.get(i).get(j)).execute(remainingSections.get(i), nextSectionSet, caster.getPosition(), caster);
                    executed = true;
                    break;
                }
            }
            if(executed) break;
        }
        return executed;
    }
}
