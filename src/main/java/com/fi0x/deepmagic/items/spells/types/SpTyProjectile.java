package com.fi0x.deepmagic.items.spells.types;

import com.fi0x.deepmagic.items.spells.ISpellPart;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class SpTyProjectile implements ISpellType
{
    public static final String NAME = "type_projectile";
    private double existingDuration = 5;
    private double range = 16;
    private double velocity = 1;

    @Override
    public String getName()
    {
        return NAME;
    }
    @Override
    public String getPartAsString()
    {
        String ret = NAME + "_attr_";
        ret += existingDuration + "_attr_";
        ret += range + "_attr_";
        ret += velocity;
        return ret;
    }
    @Override
    public void setAttributesFromString(ArrayList<String> attributes)
    {
        existingDuration = Double.parseDouble(attributes.get(0));
        range = Double.parseDouble(attributes.get(1));
        velocity = Double.parseDouble(attributes.get(2));
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
        //TODO: Execute spell
    }

    @Override
    public void setDuration(double value)
    {
        existingDuration = value;
    }
    @Override
    public double getDuration()
    {
        return existingDuration;
    }
    @Override
    public void setRange(double value)
    {
        range = value;
    }
    @Override
    public double getRange()
    {
        return range;
    }
    @Override
    public void setVelocity(double value)
    {
        velocity = value;
    }
    @Override
    public double getVelocity()
    {
        return velocity;
    }
}
