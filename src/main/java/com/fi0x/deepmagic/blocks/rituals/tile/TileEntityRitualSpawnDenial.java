package com.fi0x.deepmagic.blocks.rituals.tile;

import com.fi0x.deepmagic.blocks.rituals.RITUAL_TYPE;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;

public class TileEntityRitualSpawnDenial extends TileEntityRitualStone
{
    public TileEntityRitualSpawnDenial()
    {
        type = RITUAL_TYPE.SPAWN_DENIAL;
        manaCosts = ConfigHandler.ritualSpawnDenialManaCosts;
    }

    @Override
    protected void syncedUpdate()
    {
        /*
        TODO: Add a capability to worlds that stores locations of all rituals
         Add block locations on block placement and remove them on block destruction
         Deny spawns around these locations for hostile mobs
         */
    }
}