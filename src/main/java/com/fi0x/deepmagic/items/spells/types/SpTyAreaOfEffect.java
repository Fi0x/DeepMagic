package com.fi0x.deepmagic.items.spells.types;

import net.minecraft.util.math.BlockPos;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;

public class SpTyAreaOfEffect implements ISpellType
{
    @Override
    public String getName()
    {
        return "type_aoe";
    }
    @Override
    public ISpellType getType()
    {
        return this;
    }
    @Override
    public ArrayList<Entity> getEntities()
    {
        //TODO: Return entities in area
        return null;
    }
    @Override
    public ArrayList<BlockPos> getPositions()
    {
        //TODO: Return position
        return null;
    }
    @Override
    public boolean requiresPosition()
    {
        return true;
    }
    @Override
    public double getRadius()
    {
        //TODO: Adjust value
        return 1;
    }
}
