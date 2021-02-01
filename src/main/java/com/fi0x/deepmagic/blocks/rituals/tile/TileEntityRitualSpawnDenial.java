package com.fi0x.deepmagic.blocks.rituals.tile;

import com.fi0x.deepmagic.util.handlers.ConfigHandler;

public class TileEntityRitualSpawnDenial extends TileEntityRitualStone
{
    public TileEntityRitualSpawnDenial()
    {
        manaCosts = ConfigHandler.ritualSpawnDenialManaCosts;
    }

    @Override
    protected void syncedUpdate()
    {
        if(storedMana >= manaCosts)
        {
            storedMana -= manaCosts;
            //TODO: Deny spawns
        }
    }
}