package com.fi0x.deepmagic.items.spells.types;

import net.minecraft.util.math.BlockPos;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;

public class SpTySelf implements ISpellType
{
    @Override
    public String getName()
    {
        return "type_self";
    }
    @Override
    public ISpellType getType()
    {
        return this;
    }
    @Override
    public ArrayList<Entity> getEntities()
    {
        //TODO: Return caster
        return null;
    }
    @Override
    public ArrayList<BlockPos> getPositions()
    {
        return null;
    }
    @Override
    public boolean requiresEntity()
    {
        return true;
    }
}
