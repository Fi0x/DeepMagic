package com.fi0x.deepmagic.items.spells.types;

import com.fi0x.deepmagic.items.spells.ISpellPart;
import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class SpTySelf implements ISpellType
{
    public static final String NAME = "type_self";

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME;
        return ret;
    }
    @Override
    public ISpellType getType()
    {
        return this;
    }

    @Override
    public void execute(ArrayList<ISpellPart> applicableParts, ArrayList<ArrayList<ISpellPart>> remainingSections, BlockPos castLocation, @Nullable EntityLivingBase caster, World world)
    {
        if(!applicableParts.isEmpty()) applicableParts.remove(0);

        while(!applicableParts.isEmpty())
        {
            if(caster != null && applicableParts.get(0) instanceof ISpellEffect)
            {
                ((ISpellEffect) applicableParts.get(0)).applyEffect(caster, castLocation, world);
                ((ISpellEffect) applicableParts.get(0)).applyEffect(caster, caster);
            } else if(applicableParts.get(0) instanceof ISpellType)
            {
                ((ISpellType) applicableParts.get(0)).execute(applicableParts, remainingSections, castLocation, caster, world);
                break;
            }
            applicableParts.remove(0);
        }
    }
}