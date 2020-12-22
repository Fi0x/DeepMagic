package com.fi0x.deepmagic.items.spells.types;

import net.minecraft.util.math.BlockPos;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;

public class SpTyIterate implements ISpellType
{
    @Override
    public String getName()
    {
        return "type_iterate";
    }
    @Override
    public ISpellType getType()
    {
        return this;
    }
    @Override
    public ArrayList<Entity> getEntities()
    {
        return null;
    }
    @Override
    public ArrayList<BlockPos> getPositions()
    {
        return null;
    }
    @Override
    public boolean triggersOnce()
    {
        return false;
    }
}
