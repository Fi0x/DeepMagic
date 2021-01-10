package com.fi0x.deepmagic.items.spells.types;

import com.fi0x.deepmagic.items.spells.CastHelper;
import com.fi0x.deepmagic.items.spells.ISpellPart;
import com.fi0x.deepmagic.items.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class SpTyAreaOfEffect implements ISpellType
{
    public static final String NAME = "type_aoe";
    private double radius = 1;

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
    public void execute(ArrayList<ISpellPart> applicableParts, ArrayList<ArrayList<ISpellPart>> remainingSections, BlockPos castLocation, EntityLivingBase caster, World world)
    {
        applicableParts.remove(0);
        ArrayList<BlockPos> targetPositions = getPositions(castLocation);
        boolean executed = false;

        while(!applicableParts.isEmpty())
        {
            if(applicableParts.get(0) instanceof ISpellEffect)
            {
                for(BlockPos targetPos : targetPositions)
                {
                    ((ISpellEffect) applicableParts.get(0)).applyEffect(caster, targetPos, world);
                }
            } else if(applicableParts.get(0) instanceof ISpellType)
            {
                for(BlockPos targetPos : targetPositions)
                {
                    ((ISpellType) applicableParts.get(0)).execute(applicableParts, remainingSections, targetPos, caster, world);
                }
                executed = true;
            }
            if(executed) break;
            applicableParts.remove(0);
        }

        if(!executed)
        {
            new CastHelper().findAndCastNextSpellType(remainingSections, castLocation, caster);
        }
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

    private ArrayList<BlockPos> getPositions(BlockPos pos)
    {
        ArrayList<BlockPos> blocks = new ArrayList<>();
        for(int x = (int) (pos.getX() - (radius - 1)); x < pos.getX() + (radius - 1); x++)
        {
            for(int y = (int) (pos.getY() - (radius - 1)); y < pos.getY() + (radius - 1); y++)
            {
                for(int z = (int) (pos.getZ() - (radius - 1)); z < pos.getZ() + (radius - 1); z++)
                {
                    blocks.add(new BlockPos(x, y, z));
                }
            }
        }

        ArrayList<BlockPos> ret = new ArrayList<>();
        for(BlockPos e : blocks)
        {
            if(e.distanceSq(pos.getX(), pos.getY(), pos.getZ()) <= radius * radius)
            {
                ret.add(e);
            }
        }

        return ret;
    }
}
