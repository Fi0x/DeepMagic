package com.fi0x.deepmagic.world;

import com.fi0x.deepmagic.blocks.mana.ManaRelay;
import com.fi0x.deepmagic.blocks.rituals.RITUAL_TYPE;
import com.fi0x.deepmagic.blocks.rituals.structureblocks.RitualStructure;
import com.fi0x.deepmagic.particlesystem.ParticleEnum;
import com.fi0x.deepmagic.particlesystem.ParticleSpawner;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.BlockGlowstone;
import net.minecraft.block.BlockObsidian;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureChecker
{
    public static boolean verifyRitualStructure(World world, BlockPos pos, RITUAL_TYPE type)
    {
        if(!ConfigHandler.requireRitualStructure) return true;
        boolean valid = true;

        for(BlockPos offset : ritualFloorLocations)
        {
            BlockPos position = pos.add(offset);
            if(world.getBlockState(position).getBlock() instanceof RitualStructure) continue;
            ParticleSpawner.spawnParticle(ParticleEnum.RITUAL_MISSING, position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
            valid = false;
        }
        for(BlockPos offset : ritualArmLocations)
        {
            BlockPos position = pos.add(offset);
            if(world.getBlockState(position).getBlock() instanceof RitualStructure) continue;
            ParticleSpawner.spawnParticle(ParticleEnum.RITUAL_MISSING, position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
            valid = false;
        }

        return valid;
    }
    public static boolean verifyDemonStructure(World world, BlockPos pos)
    {
        if(!ConfigHandler.requireDemonStructure) return true;
        boolean valid = true;

        for(BlockPos offset : demonLocationsDemonCrystal)
        {
            BlockPos position = pos.add(offset);
            if(world.getBlockState(position).getBlock().getUnlocalizedName().equals("tile.demon_crystal_block")) continue;
            ParticleSpawner.spawnParticle(ParticleEnum.RITUAL_MISSING, position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
            valid = false;
        }
        for(BlockPos offset : demonLocationsDeepCrystal)
        {
            BlockPos position = pos.add(offset);
            if(world.getBlockState(position).getBlock().getUnlocalizedName().equals("tile.deep_crystal_block")) continue;
            ParticleSpawner.spawnParticle(ParticleEnum.RITUAL_MISSING, position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
            valid = false;
        }
        for(BlockPos offset : demonLocationsIronBlock)
        {
            BlockPos position = pos.add(offset);
            if(world.getBlockState(position).getBlock().getUnlocalizedName().equals("tile.blockIron")) continue;
            ParticleSpawner.spawnParticle(ParticleEnum.RITUAL_MISSING, position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
            valid = false;
        }

        return valid;
    }
    public static boolean verifySpellStoneStructure(World world, BlockPos pos)
    {
        if(!ConfigHandler.requireSpellStoneStructure) return true;
        boolean valid = true;

        for(BlockPos offset : spellStoneLocationsRelay)
        {
            BlockPos position = pos.add(offset);
            if(world.getBlockState(position).getBlock() instanceof ManaRelay) continue;
            ParticleSpawner.spawnParticle(ParticleEnum.RITUAL_MISSING, position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
            valid = false;
        }
        for(BlockPos offset : spellStoneLocationsGlowstone)
        {
            BlockPos position = pos.add(offset);
            if(world.getBlockState(position).getBlock() instanceof BlockGlowstone) continue;
            ParticleSpawner.spawnParticle(ParticleEnum.RITUAL_MISSING, position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
            valid = false;
        }
        for(BlockPos offset : spellStoneLocationsDeepCrystal)
        {
            BlockPos position = pos.add(offset);
            if(world.getBlockState(position).getBlock().getUnlocalizedName().equals("tile.deep_crystal_block")) continue;
            ParticleSpawner.spawnParticle(ParticleEnum.RITUAL_MISSING, position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
            valid = false;
        }
        for(BlockPos offset : spellStoneLocationsObsidian)
        {
            BlockPos position = pos.add(offset);
            if(world.getBlockState(position).getBlock() instanceof BlockObsidian) continue;
            ParticleSpawner.spawnParticle(ParticleEnum.RITUAL_MISSING, position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
            valid = false;
        }

        return valid;
    }

    public static final BlockPos[] ritualFloorLocations = new BlockPos[]{
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
    public static final BlockPos[] ritualArmLocations = new BlockPos[]{
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

    private static final BlockPos[] demonLocationsDeepCrystal = new BlockPos[]{
            new BlockPos(1, -1, 0),
            new BlockPos(-1, -1, 0),
            new BlockPos(0, -1, 1),
            new BlockPos(0, -1, -1)
    };
    private static final BlockPos[] demonLocationsDemonCrystal = new BlockPos[]{
            new BlockPos(0, -1, 0)
    };
    private static final BlockPos[] demonLocationsIronBlock = new BlockPos[]{
            new BlockPos(1, -1, 1),
            new BlockPos(1, -1, -1),
            new BlockPos(-1, -1, 1),
            new BlockPos(-1, -1, -1)
    };

    private static final BlockPos[] spellStoneLocationsRelay = new BlockPos[]{
            new BlockPos(0, -1, 0)
    };
    private static final BlockPos[] spellStoneLocationsGlowstone = new BlockPos[]{
            new BlockPos(1, 0, 0),
            new BlockPos(-1, 0, 0),
            new BlockPos(0, 0, 1),
            new BlockPos(0, 0, -1)
    };
    private static final BlockPos[] spellStoneLocationsDeepCrystal = new BlockPos[]{
            new BlockPos(1, 0, 1),
            new BlockPos(-1, 0, 1),
            new BlockPos(1, 0, -1),
            new BlockPos(-1, 0, -1)
    };
    private static final BlockPos[] spellStoneLocationsObsidian = new BlockPos[]{
            new BlockPos(2, -1, 0),
            new BlockPos(-2, -1, 0),
            new BlockPos(0, -1, 2),
            new BlockPos(0, -1, -2),
            new BlockPos(2, -1, 2),
            new BlockPos(2, -1, -2),
            new BlockPos(-2, -1, 2),
            new BlockPos(-2, -1, -2)
    };
}