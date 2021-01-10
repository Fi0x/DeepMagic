package com.fi0x.deepmagic.items.spells.types;

import com.fi0x.deepmagic.items.spells.CastHelper;
import com.fi0x.deepmagic.items.spells.ISpellPart;
import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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
    public void execute(ArrayList<ISpellPart> applicableParts, ArrayList<ArrayList<ISpellPart>> remainingSections, BlockPos castLocation, @Nullable EntityLivingBase caster, World world)
    {
        applicableParts.remove(0);
        boolean executed = false;
        BlockPos targetPos = getTouchedPos(caster, castLocation);
        List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(caster, new AxisAlignedBB(targetPos));
        EntityLivingBase target = null;
        while(!entities.isEmpty())
        {
            if(entities.get(0) instanceof EntityLivingBase)
            {
                target = (EntityLivingBase) entities.get(0);
                break;
            }
        }

        while(!applicableParts.isEmpty())
        {
            if(applicableParts.get(0) instanceof ISpellEffect)
            {
                ((ISpellEffect) applicableParts.get(0)).applyEffect(caster, targetPos, world);
                if(target != null) ((ISpellEffect) applicableParts.get(0)).applyEffect(caster, target);
            } else if(applicableParts.get(0) instanceof ISpellType)
            {
                ((ISpellType) applicableParts.get(0)).execute(applicableParts, remainingSections, targetPos, caster, world);
                executed = true;
            }
            if(executed) break;
            applicableParts.remove(0);
        }

        if(!executed)
        {
            new CastHelper().findAndCastNextSpellType(remainingSections, targetPos, caster, world);
        }
    }

    private BlockPos getTouchedPos(@Nullable EntityLivingBase caster, BlockPos castLocation)
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
