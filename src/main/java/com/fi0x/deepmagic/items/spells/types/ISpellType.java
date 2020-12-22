package com.fi0x.deepmagic.items.spells.types;

import com.fi0x.deepmagic.items.spells.ISpellPart;
import net.minecraft.util.math.BlockPos;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;

public interface ISpellType extends ISpellPart
{
    default boolean requiresPosition()
    {
        return false;
    }
    default boolean requiresEntity()
    {
        return false;
    }
    default boolean triggersOnce()
    {
        return true;
    }

    ArrayList<Entity> getEntities();
    ArrayList<BlockPos> getPositions();
    default double getDuration()
    {
        return 0;
    }
    default double getRange()
    {
        return 0;
    }
    default double getRadius()
    {
        return 0;
    }
}
