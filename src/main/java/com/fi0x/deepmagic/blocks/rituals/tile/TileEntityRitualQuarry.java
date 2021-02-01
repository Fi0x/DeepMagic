package com.fi0x.deepmagic.blocks.rituals.tile;

import com.fi0x.deepmagic.util.handlers.ConfigHandler;

public class TileEntityRitualQuarry extends TileEntityRitualStone
{
    public TileEntityRitualQuarry()
    {
        manaCosts = ConfigHandler.ritualQuarryManaCosts;
    }

    @Override
    protected void syncedUpdate()
    {
        if(storedMana >= manaCosts)
        {
            storedMana -= manaCosts;
            /*
            TODO: Dig blocks
             Pack Quarry together
             Move Quarry
             Unpack Quarry
             Use a redstone build block to have the quarry active
             */
        }
    }
}