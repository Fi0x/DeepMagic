package com.fi0x.deepmagic.items.spells.types;

import com.fi0x.deepmagic.items.spells.ISpellPart;
import net.minecraft.util.math.BlockPos;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;

public interface ISpellType extends ISpellPart
{
    @Override
    default boolean isType()
    {
        return true;
    }

    boolean providesEntity();
    boolean providesPosition();
    boolean triggersOnce();
    boolean hasDuration();
    boolean hasRange();
    boolean hasRadius();

    ArrayList<Entity> getEntities();
    ArrayList<BlockPos> getPositions();
    double getDuration();
    double getRange();
    double getRadius();
}
