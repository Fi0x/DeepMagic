package com.fi0x.deepmagic.items.spells;

import com.fi0x.deepmagic.items.spells.types.ISpellType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class CastHelper
{
    public void findAndCastSpellTypes(ArrayList<ArrayList<ISpellPart>> remainingSections, BlockPos castLocation, @Nullable EntityLivingBase caster, World world)
    {
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

                    ((ISpellType) remainingSections.get(i).get(j)).execute(remainingSections.get(i), nextSectionSet, castLocation, caster, world);
                    break;
                }
            }
        }
    }
}
