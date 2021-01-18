package com.fi0x.deepmagic.world.generators.underground;

import net.minecraft.world.gen.structure.MapGenMineshaft;

public class CustomMineshaftGenerator extends MapGenMineshaft
{
    //TODO: Adjust mineshaft design
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        return this.rand.nextDouble() < 0.01;
    }
}
