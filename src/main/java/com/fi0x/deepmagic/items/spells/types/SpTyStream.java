package com.fi0x.deepmagic.items.spells.types;

import net.minecraft.util.math.BlockPos;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;

/*
 * Constant stream of the effect
 * Like the beam, but not as focused
 */
public class SpTyStream implements ISpellType
{
    @Override
    public String getName()
    {
        return "type_stream";
    }
    @Override
    public ISpellType getType()
    {
        return this;
    }
    @Override
    public ArrayList<Entity> getEntities()
    {
        //TODO: Return hit-entities
        return null;
    }
    @Override
    public ArrayList<BlockPos> getPositions()
    {
        //TODO: Return reached positions
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
        return 4;
    }
    @Override
    public double getRadius()
    {
        return 1;
    }
}
