package com.fi0x.deepmagic.items.spells.types;

import com.fi0x.deepmagic.items.spells.CastHelper;
import com.fi0x.deepmagic.items.spells.ISpellPart;
import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

import java.util.ArrayList;

public class SpTyTouch implements ISpellType
{
    public static final String NAME = "type_touch";

    @Override
    public String getName()
    {
        return NAME;
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
        BlockPos targetPos = getTouchedPos(caster, castLocation);

        while(!applicableParts.isEmpty())
        {
            if(applicableParts.get(0) instanceof ISpellEffect)
            {
                ((ISpellEffect) applicableParts.get(0)).applyEffect(caster, targetPos, caster.world);
            } else if(applicableParts.get(0) instanceof ISpellType)
            {
                ((ISpellType) applicableParts.get(0)).execute(applicableParts, remainingSections, targetPos, caster);
                executed = true;
            }
            if(executed) break;
            applicableParts.remove(0);
        }

        if(!executed)
        {
            new CastHelper().findAndCastNextSpellType(remainingSections, targetPos, caster);
        }
    }

    private BlockPos getTouchedPos(EntityLivingBase caster, BlockPos castLocation)
    {
        BlockPos ret = castLocation;

        RayTraceResult lookingAt = Minecraft.getMinecraft().objectMouseOver;
        if(lookingAt != null && lookingAt.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            ret = lookingAt.getBlockPos();
            //TODO: Check if server-client sync works
        }

        return ret;
    }
}
