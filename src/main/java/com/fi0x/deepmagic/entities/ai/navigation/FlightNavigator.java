package com.fi0x.deepmagic.entities.ai.navigation;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.FlyingNodeProcessor;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class FlightNavigator extends PathNavigateFlying
{
    public FlightNavigator(EntityLiving entityIn, World worldIn)
    {
        super(entityIn, worldIn);
    }

    @Nonnull
    @Override
    protected PathFinder getPathFinder()
    {
        this.nodeProcessor = new FlyingNodeProcessor();
        this.nodeProcessor.setCanEnterDoors(true);
        return new PathFinder(this.nodeProcessor);
    }
    @Override
    protected boolean canNavigate()
    {
        return true;
    }
    @Override
    protected boolean isDirectPathBetweenPoints(@Nonnull Vec3d posVec31, @Nonnull Vec3d posVec32, int sizeX, int sizeY, int sizeZ)
    {
        return super.isDirectPathBetweenPoints(posVec31, posVec32, sizeX, sizeY, sizeZ);
    }

    @Override
    public boolean canEntityStandOnPos(BlockPos pos)
    {
        return true;
    }
}