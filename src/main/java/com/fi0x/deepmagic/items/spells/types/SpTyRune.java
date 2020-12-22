package com.fi0x.deepmagic.items.spells.types;

import net.minecraft.util.math.BlockPos;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;

public class SpTyRune implements ISpellType
{
    @Override
    public String getName()
    {
        return "type_rune";
    }
    @Override
    public ISpellType getType()
    {
        return this;
    }
    @Override
    public ArrayList<Entity> getEntities()
    {
        //TODO: Return triggering entity
        return null;
    }
    @Override
    public ArrayList<BlockPos> getPositions()
    {
        //TODO: Return own position
        return null;
    }
    @Override
    public boolean requiresPosition()
    {
        return true;
    }
}
