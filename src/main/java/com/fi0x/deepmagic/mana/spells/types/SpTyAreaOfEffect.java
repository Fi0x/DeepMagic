package com.fi0x.deepmagic.mana.spells.types;

import com.fi0x.deepmagic.mana.spells.ISpellPart;
import com.fi0x.deepmagic.mana.spells.effects.ISpellEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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
    public String getDisplayName()
    {
        return "Area of Effect";
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME + "_attr_";
        ret += radius;
        return ret;
    }
    @Override
    public void setAttributesFromString(ArrayList<String> attributes)
    {
        radius = Double.parseDouble(attributes.get(0));
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
        ArrayList<BlockPos> targetPositions = getPositions(castLocation);
        ArrayList<EntityLivingBase> targets = getEntities(world, castLocation);

        while(!applicableParts.isEmpty())
        {
            if(applicableParts.get(0) instanceof ISpellEffect)
            {
                for(BlockPos targetPos : targetPositions)
                {
                    ((ISpellEffect) applicableParts.get(0)).applyEffect(caster, targetPos, world);
                }
                for(EntityLivingBase target : targets)
                {
                    ((ISpellEffect) applicableParts.get(0)).applyEffect(caster, target);
                }
            } else if(applicableParts.get(0) instanceof ISpellType)
            {
                for(BlockPos targetPos : targetPositions)
                {
                    ((ISpellType) applicableParts.get(0)).execute(applicableParts, remainingSections, targetPos, caster, world);
                }
                break;
            }
            applicableParts.remove(0);
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
            if(e.distanceSq(pos) <= radius * radius) ret.add(e);
        }

        return ret;
    }
    private ArrayList<EntityLivingBase> getEntities(World world, BlockPos pos)
    {
        AxisAlignedBB aabb = new AxisAlignedBB(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius));
        List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, aabb);

        ArrayList<EntityLivingBase> ret = new ArrayList<>();
        for(EntityLivingBase entity : entities)
        {
            if(entity.getDistanceSq(pos) <= radius * radius) ret.add(entity);
        }

        return ret;
    }
}
