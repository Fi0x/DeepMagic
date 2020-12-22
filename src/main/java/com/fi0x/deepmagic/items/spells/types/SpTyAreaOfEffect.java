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
        return null;
    }
    @Override
    public ArrayList<BlockPos> getPositions()
    {
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
        return 1;
    }

    public ArrayList<Entity> getEntities(BlockPos pos)
    {
        ArrayList<Entity> list = new ArrayList<>();
        //TODO: Return entities in area
        return list;
    }
    public ArrayList<BlockPos> getPositions(BlockPos pos)
    {
        ArrayList<BlockPos> list = new ArrayList<>();

        //TODO: Return positions
        list.add(pos);

        return list;
    }
}
