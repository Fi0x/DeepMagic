package com.fi0x.deepmagic.entities.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FlightNavigator extends PathNavigateFlying
{
    public FlightNavigator(EntityLiving entityIn, World worldIn)
    {
        super(entityIn, worldIn);
    }

    @Override
    protected PathFinder getPathFinder()
    {
        return null;
    }
    @Override
    protected Vec3d getEntityPosition()
    {
        return null;
    }
    @Override
    protected boolean canNavigate()
    {
        return false;
    }
    @Override
    protected boolean isDirectPathBetweenPoints(Vec3d posVec31, Vec3d posVec32, int sizeX, int sizeY, int sizeZ)
    {
        return false;
    }
}