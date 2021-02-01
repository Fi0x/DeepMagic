package com.fi0x.deepmagic.blocks.rituals.tile;

import com.fi0x.deepmagic.blocks.rituals.RitualBuildBlock;
import com.fi0x.deepmagic.particlesystem.ParticleEnum;
import com.fi0x.deepmagic.particlesystem.ParticleSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualStructureLayout
{
    public static boolean verifyStructure(World world, BlockPos pos)
    {
        boolean valid = true;

        for(BlockPos offset : floorLocations)
        {
            BlockPos position = pos.add(offset);
            if(world.getBlockState(position).getBlock() instanceof RitualBuildBlock) continue;
            ParticleSpawner.spawnParticle(ParticleEnum.RITUAL_MISSING, position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
            valid = false;
        }
        for(BlockPos offset : armLocations)
        {
            BlockPos position = pos.add(offset);
            if(world.getBlockState(position).getBlock() instanceof RitualBuildBlock) continue;
            ParticleSpawner.spawnParticle(ParticleEnum.RITUAL_MISSING, position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
            valid = false;
        }

        return valid;
    }

    private static BlockPos[] floorLocations = new BlockPos[]{
            new BlockPos(0, -1, 0),
            new BlockPos(1, -1, 0),
            new BlockPos(2, -1, 0),
            new BlockPos(3, -1, 0),
            new BlockPos(4, -1, 0),
            new BlockPos(-1, -1, 0),
            new BlockPos(-2, -1, 0),
            new BlockPos(-3, -1, 0),
            new BlockPos(-4, -1, 0),
            new BlockPos(0, -1, 1),
            new BlockPos(1, -1, 1),
            new BlockPos(2, -1, 1),
            new BlockPos(3, -1, 1),
            new BlockPos(4, -1, 1),
            new BlockPos(-1, -1, 1),
            new BlockPos(-2, -1, 1),
            new BlockPos(-3, -1, 1),
            new BlockPos(-4, -1, 1),
            new BlockPos(0, -1, -1),
            new BlockPos(1, -1, -1),
            new BlockPos(2, -1, -1),
            new BlockPos(3, -1, -1),
            new BlockPos(4, -1, -1),
            new BlockPos(-1, -1, -1),
            new BlockPos(-2, -1, -1),
            new BlockPos(-3, -1, -1),
            new BlockPos(-4, -1, -1),
            new BlockPos(0, -1, 2),
            new BlockPos(1, -1, 2),
            new BlockPos(2, -1, 2),
            new BlockPos(3, -1, 2),
            new BlockPos(-1, -1, 2),
            new BlockPos(-2, -1, 2),
            new BlockPos(-3, -1, 2),
            new BlockPos(0, -1, -2),
            new BlockPos(1, -1, -2),
            new BlockPos(2, -1, -2),
            new BlockPos(3, -1, -2),
            new BlockPos(-1, -1, -2),
            new BlockPos(-2, -1, -2),
            new BlockPos(-3, -1, -2),
            new BlockPos(0, -1, 3),
            new BlockPos(1, -1, 3),
            new BlockPos(2, -1, 3),
            new BlockPos(-1, -1, 3),
            new BlockPos(-2, -1, 3),
            new BlockPos(0, -1, -3),
            new BlockPos(1, -1, -3),
            new BlockPos(2, -1, -3),
            new BlockPos(-1, -1, -3),
            new BlockPos(-2, -1, -3),
            new BlockPos(0, -1, 4),
            new BlockPos(1, -1, 4),
            new BlockPos(-1, -1, 4),
            new BlockPos(0, -1, -4),
            new BlockPos(1, -1, -4),
            new BlockPos(-1, -1, -4)
    };
    private static BlockPos[] armLocations = new BlockPos[]{
            new BlockPos(0, 0, 4),
            new BlockPos(0, 1, 4),
            new BlockPos(0, 2, 3),
            new BlockPos(0, 3, 2),
            new BlockPos(0, 3, 1),
            new BlockPos(0, 0, -4),
            new BlockPos(0, 1, -4),
            new BlockPos(0, 2, -3),
            new BlockPos(0, 3, -2),
            new BlockPos(0, 3, -1),
            new BlockPos(4, 0, 0),
            new BlockPos(4, 1, 0),
            new BlockPos(3, 2, 0),
            new BlockPos(2, 3, 0),
            new BlockPos(1, 3, 0),
            new BlockPos(-4, 0, 0),
            new BlockPos(-4, 1, 0),
            new BlockPos(-3, 2, 0),
            new BlockPos(-2, 3, 0),
            new BlockPos(-1, 3, 0)
    };
}