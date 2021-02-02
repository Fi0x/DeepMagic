package com.fi0x.deepmagic.world;

import com.fi0x.deepmagic.blocks.rituals.RitualBuildBlock;
import com.fi0x.deepmagic.particlesystem.ParticleEnum;
import com.fi0x.deepmagic.particlesystem.ParticleSpawner;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureChecker
{
    public static boolean verifyRitualStructure(World world, BlockPos pos)
    {
        if(!ConfigHandler.requireRitualStructure) return true;
        boolean valid = true;

        for(BlockPos offset : ritualFloorLocations)
        {
            BlockPos position = pos.add(offset);
            if(world.getBlockState(position).getBlock() instanceof RitualBuildBlock) continue;
            ParticleSpawner.spawnParticle(ParticleEnum.RITUAL_MISSING, position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
            valid = false;
        }
        for(BlockPos offset : ritualArmLocations)
        {
            BlockPos position = pos.add(offset);
            if(world.getBlockState(position).getBlock() instanceof RitualBuildBlock) continue;
            ParticleSpawner.spawnParticle(ParticleEnum.RITUAL_MISSING, position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
            valid = false;
        }

        return valid;
    }
    public static boolean verifyDemonStructure(World world, BlockPos pos)
    {
        if(!ConfigHandler.requireDemonStructure) return true;
        for(BlockPos offset : demonLocationsDemonCrystal)
        {
            BlockPos position = pos.add(offset);
            if(world.getBlockState(position).getBlock().getUnlocalizedName().equals("tile.demon_crystal_block")) continue;
            ParticleSpawner.spawnParticle(ParticleEnum.RITUAL_MISSING, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
            return false;
        }
        for(BlockPos offset : demonLocationsDeepCrystal)
        {
            BlockPos position = pos.add(offset);
            if(world.getBlockState(position).getBlock().getUnlocalizedName().equals("tile.deep_crystal_block")) continue;
            ParticleSpawner.spawnParticle(ParticleEnum.RITUAL_MISSING, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
            return false;
        }
        for(BlockPos offset : demonLocationsIronBlock)
        {
            BlockPos position = pos.add(offset);
            if(world.getBlockState(position).getBlock().getUnlocalizedName().equals("tile.blockIron")) continue;
            ParticleSpawner.spawnParticle(ParticleEnum.RITUAL_MISSING, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
            return false;
        }

        return true;
    }

    private static BlockPos[] ritualFloorLocations = new BlockPos[]{
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
    private static BlockPos[] ritualArmLocations = new BlockPos[]{
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

    private static BlockPos[] demonLocationsDeepCrystal = new BlockPos[]{
            new BlockPos(1, -1, 0),
            new BlockPos(-1, -1, 0),
            new BlockPos(0, -1, 1),
            new BlockPos(0, -1, -1)
    };
    private static BlockPos[] demonLocationsDemonCrystal = new BlockPos[]{
            new BlockPos(0, -1, 0)
    };
    private static BlockPos[] demonLocationsIronBlock = new BlockPos[]{
            new BlockPos(1, -1, 1),
            new BlockPos(1, -1, -1),
            new BlockPos(-1, -1, 1),
            new BlockPos(-1, -1, -1)
    };
}