package com.fi0x.deepmagic.items.spells.types;

import net.minecraft.util.math.BlockPos;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;

public class SpTyProjectile implements ISpellType
{
    @Override
    public String getName()
    {
        return "type_projectile";
    }
    @Override
    public ISpellType getType()
    {
        return this;
    }
    @Override
    public ArrayList<Entity> getEntities()
    {
        //TODO: Return hit entity
        return null;
    }
    @Override
    public ArrayList<BlockPos> getPositions()
    {
        //TODO: Return hit position
        return null;
    }
    @Override
    public boolean requiresEntity()
    {
        return true;
    }
    @Override
    public double getRange()
    {
        //TODO: Adjust value
        return 1;
    }
    @Override
    public double getRadius()
    {
        //TODO: Adjust value
        return 1;
    }
}
