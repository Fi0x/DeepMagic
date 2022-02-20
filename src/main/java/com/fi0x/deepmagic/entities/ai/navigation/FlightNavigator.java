package com.fi0x.deepmagic.entities.ai.navigation;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.FlyingNodeProcessor;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

    @Override
    public void onUpdateNavigation()
    {
        this.totalTicks++;

        if (this.tryUpdatePath)
            this.updatePath();

        if (!this.noPath())
        {
            if (this.canNavigate())
                this.pathFollow();
            else if (this.currentPath != null && this.currentPath.getCurrentPathIndex() < this.currentPath.getCurrentPathLength())
            {
                Vec3d vec3d = this.currentPath.getVectorFromIndex(this.entity, this.currentPath.getCurrentPathIndex());

                if (MathHelper.floor(this.entity.posX) == MathHelper.floor(vec3d.x) && MathHelper.floor(this.entity.posY) == MathHelper.floor(vec3d.y) && MathHelper.floor(this.entity.posZ) == MathHelper.floor(vec3d.z))
                    this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1);

            }

            this.debugPathFinding();

            if (!this.noPath())
            {
                Vec3d vec3d1 = this.currentPath.getPosition(this.entity);
                this.entity.getMoveHelper().setMoveTo(vec3d1.x, vec3d1.y, vec3d1.z, this.speed);
            }
        }
    }
}