package com.fi0x.deepmagic.world.generators.underground;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenMineshaft;

import javax.annotation.Nonnull;

public class CustomMineshaftGenerator extends MapGenMineshaft
{
    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        return this.rand.nextDouble() < 0.01;
    }
    @Override
    public BlockPos getNearestStructurePos(@Nonnull World worldIn, @Nonnull BlockPos pos, boolean findUnexplored)
    {
        return null;
    }
}
