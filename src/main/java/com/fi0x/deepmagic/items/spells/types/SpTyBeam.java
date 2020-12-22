package com.fi0x.deepmagic.items.spells.types;

import net.minecraft.util.math.BlockPos;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;

public class SpTyBeam implements ISpellType
{
    @Override
    public String getName()
    {
        return "type_beam";
    }
    @Override
    public ISpellType getType()
    {
        return this;
    }
    @Override
    public ArrayList<Entity> getEntities()
    {
        //TODO: Return hit entities
        return null;
    }
    @Override
    public ArrayList<BlockPos> getPositions()
    {
        //TODO: Return hit positions
        return null;
    }
    @Override
    public boolean requiresEntity()
    {
        return true;
    }
    @Override
    public boolean triggersOnce()
    {
        return false;
    }
    @Override
    public double getRange()
    {
        //TODO: Adjust value
        return 1;
    }
}
