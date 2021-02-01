package com.fi0x.deepmagic.world.generators;

import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.util.Reference;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.world.biomes.depth.BiomeDepth;
import com.fi0x.deepmagic.world.biomes.insanity.*;
import com.fi0x.deepmagic.world.generators.dungeon.SmallDungeon;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class WorldGenCustomStructures implements IWorldGenerator
{
    public static final ModWorldGenStructure MAGE_HOUSE = new ModWorldGenStructure("mage_house");
    public static final ModWorldGenStructure MAGE_HOUSE_SMALL = new ModWorldGenStructure("mage_house_small");
    public static final ModWorldGenStructure INSANITY_ROCK_TROLL_CAVE = new ModWorldGenStructure("insanity_rock_troll_cave");
    public static final ModWorldGenStructure SHRINE = new ModWorldGenStructure("shrine");
    public static final ModWorldGenStructure INSANITY_OASIS = new ModWorldGenStructure("insanity_oasis");
    public static final ModWorldGenStructure DWARF_BASE = new ModWorldGenStructure("dwarf_base");
    public static final ModWorldGenStructure DRAGON_LAIR = new ModWorldGenStructure("dragon_lair");//TODO: Reduce size

    public static final SmallDungeon DUNGEON = new SmallDungeon();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if(world.provider.getDimension() == ConfigHandler.dimensionIdInsanityID)
        {
            if(ConfigHandler.generateMageHouses) generateStructure(MAGE_HOUSE_SMALL, world, random, chunkX, chunkZ, -4, 0, 500, 11, 13, BiomeInsanityPlains.class, BiomeInsanityForestSmall.class, BiomeInsanityForestMixed.class, BiomeInsanityForestLarge.class);
            if(ConfigHandler.generateMageHouses) generateStructure(MAGE_HOUSE, world, random, chunkX, chunkZ, -2, 1, 1000, 24, 31, BiomeInsanityPlains.class);
            if(ConfigHandler.generateInsanityRockTrollCaves) generateStructure(INSANITY_ROCK_TROLL_CAVE, world, random, chunkX, chunkZ, -1, 0, 500, 15, 15, BiomeInsanityHills.class);
            if(ConfigHandler.generateShrines) generateStructure(SHRINE, world, random, chunkX, chunkZ, 0, 0, 1000, 11, 11, BiomeInsanityPlains.class, BiomeInsanityForestLarge.class, BiomeInsanityHills.class);
            if(ConfigHandler.generateInsanityOases) generateStructure(INSANITY_OASIS, world, random, chunkX, chunkZ, -1, 2, 500, 21, 21, BiomeInsanityPlains.class);
            if(ConfigHandler.generateDwarfBases) generateStructure(DWARF_BASE, world, random, chunkX, chunkZ, 0, -3, 300, 9, 9, BiomeInsanityPlains.class, BiomeInsanityForestSmall.class, BiomeInsanityForestMixed.class, BiomeInsanityForestLarge.class, BiomeInsanityHills.class);
//            if(ConfigHandler.generateDragonLairs) generateStructure(DRAGON_LAIR, world, random, chunkX, chunkZ, -2, 1, 1000, 31, 31, BiomeInsanityPlains.class);

            if(ConfigHandler.generateDungeons) generateStructure(DUNGEON, world, random, chunkX, chunkZ, 0, -20, 200, 16, 16, BiomeInsanityPlains.class, BiomeInsanityForestSmall.class, BiomeInsanityForestMixed.class, BiomeInsanityForestLarge.class);
        } else if(world.provider.getDimension() == ConfigHandler.dimensionIdDepthID)
        {
            if(ConfigHandler.generateDwarfBases) generateStructure(DWARF_BASE, world, random, chunkX, chunkZ, 0, -1, 20, 9, 9, BiomeDepth.class);

            //TODO: Use customized dungeon
//            if(ConfigHandler.generateDungeons) generateStructure(DUNGEON, world, random, chunkX, chunkZ, 0, -40, 200, 16, 16, BiomeDepth.class);
        }
    }

    private void generateStructure(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int yOffset, int heightDifference, int chance, int sizeX, int sizeZ, Class<?>... classes)
    {
        ArrayList<Class<?>> classesList = new ArrayList<>(Arrays.asList(classes));

        int x = (chunkX * 16) + random.nextInt(32 - sizeX);
        int z = (chunkZ * 16) + random.nextInt(32 - sizeZ);
        int y = calculateGenerationHeight(world, x, z) + yOffset;
        if(heightDifference < 0) y = random.nextInt(100 / Math.abs(heightDifference)) + 10;
        BlockPos pos = new BlockPos(x, y, z);

        Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();

        Template template = null;
        if(generator instanceof ModWorldGenStructure) template = ((WorldServer) world).getStructureTemplateManager().get(world.getMinecraftServer(), new ResourceLocation(Reference.MOD_ID, ((ModWorldGenStructure) generator).structureName));

        if(classesList.contains(biome) && random.nextInt(chance) == 0 && y > 10 && canSpawnHere(template, pos, heightDifference))
        {
            if(generator instanceof SmallDungeon) generator.generate(world, random, pos);
            else if(template != null) generator.generate(world, random, pos);
        }
    }

    private static int calculateGenerationHeight(World world, int x, int z)
    {
        int y = world.getHeight();
        boolean foundGround = false;

        while(!foundGround && y-- >= 0)
        {
            Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
            foundGround = block == ModBlocks.INSANITY_DIRT || block == ModBlocks.INSANITY_GRASS;
        }
        return y;
    }

    public static boolean canSpawnHere(Template template, BlockPos pos, int heightDifference)
    {
        if(heightDifference < 0) return true;
        return isCornerValid(pos, heightDifference)
                && isCornerValid(pos.add(template.getSize().getX(), 0, 0), heightDifference)
                && isCornerValid(pos.add(0, 0, template.getSize().getZ()), heightDifference)
                && isCornerValid(pos.add(template.getSize().getX(), 0, template.getSize().getZ()), heightDifference);
    }
    private static boolean isCornerValid(BlockPos pos, int heightDifference)
    {
        return pos.getY() >= pos.getY() - heightDifference && pos.getY() <= pos.getY() + heightDifference;
    }
}