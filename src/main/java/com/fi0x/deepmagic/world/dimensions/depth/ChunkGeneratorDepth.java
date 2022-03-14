package com.fi0x.deepmagic.world.dimensions.depth;

import com.fi0x.deepmagic.init.BiomeInit;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.world.dimensions.ICustomChunkGenerator;
import com.fi0x.deepmagic.world.generators.dungeon.large.LargeDungeon;
import com.fi0x.deepmagic.world.generators.underground.CustomGlowstoneGenerator;
import com.fi0x.deepmagic.world.generators.underground.DepthShaftGenerator;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ChunkGeneratorDepth implements ICustomChunkGenerator
{
    private final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
    private final IBlockState FILLER_MAIN = ModBlocks.DEPTH_STONE.getDefaultState();

    private final World world;
    private final Random rand;

    private DepthShaftGenerator shaftGenerator = new DepthShaftGenerator(ModBlocks.DEPTH_LOG.getDefaultState(), ModBlocks.DEPTH_LEAVES.getDefaultState().withProperty(BlockLeaves.DECAYABLE, Boolean.FALSE), Blocks.AIR.getDefaultState());
    private final CustomGlowstoneGenerator glowStoneGen = new CustomGlowstoneGenerator();
    private LargeDungeon largeDungeonGenerator = new LargeDungeon(this);

    public ChunkGeneratorDepth(World worldIn, long seed)
    {
        world = worldIn;
        rand = new Random(seed);
        shaftGenerator = (DepthShaftGenerator) TerrainGen.getModdedMapGen(shaftGenerator, InitMapGenEvent.EventType.CUSTOM);
        largeDungeonGenerator = (LargeDungeon) TerrainGen.getModdedMapGen(largeDungeonGenerator, InitMapGenEvent.EventType.CUSTOM);

        worldIn.setSeaLevel(63);
    }
    @Nonnull
    @Override
    public Chunk generateChunk(int x, int z)
    {
        ChunkPrimer primer = new ChunkPrimer();
        fillChunk(primer);

        if(rand.nextInt(40) == 0)
            shaftGenerator.generate(world, x, z, primer);

        if(ConfigHandler.generateDungeons)
            largeDungeonGenerator.generate(world, x, z, primer);

        Chunk chunk = new Chunk(world, primer, x, z);
        byte[] biomeArray = chunk.getBiomeArray();
        for(int i = 0; i < biomeArray.length; ++i)
        {
            biomeArray[i] = (byte) Biome.getIdForBiome(BiomeInit.DEPTH);
        }
        chunk.resetRelightChecks();
        return chunk;
    }
    private void fillChunk(ChunkPrimer primer)
    {
        for(int x = 0; x < 16; x++)
        {
            for(int z = 0; z < 16; z++)
            {
                for(int y = 128; y >= 0; y--)
                {
                    if(rand.nextInt(5) >= y) primer.setBlockState(x, y, z, BEDROCK);
                    else if(128 - rand.nextInt(5) <= y) primer.setBlockState(x, y, z, BEDROCK);
                    else primer.setBlockState(x, y, z, FILLER_MAIN);
                }
            }
        }
    }

    public void setBlocksInChunk(int x, int z, ChunkPrimer primer)
    {
    }

    @Override
    public void populate(int x, int z)
    {
        BlockFalling.fallInstantly = true;
        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, false);

        if(ConfigHandler.generateDungeons)
        {
            largeDungeonGenerator.generateStructure(world, rand, new ChunkPos(x, z));
            world.getChunkFromChunkCoords(x, z).resetRelightChecks();
        }

        int i = x * 16;
        int j = z * 16;
        BlockPos blockPos = new BlockPos(i, 0, j);
        ChunkPos chunkPos = new ChunkPos(x, z);
        Biome biome = this.world.getBiome(blockPos.add(16, 0, 16));
        this.rand.setSeed(this.world.getSeed());
        long k = this.rand.nextLong() / 2L * 2L + 1L;
        long l = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long) x * k + (long) z * l ^ this.world.getSeed());

        if(TerrainGen.populate(this, this.world, this.rand, x, z, false, PopulateChunkEvent.Populate.EventType.GLOWSTONE))
            this.glowStoneGen.generate(this.world, this.rand, blockPos.add(8, 55, 8));

        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, false);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.terraingen.DecorateBiomeEvent.Pre(this.world, this.rand, chunkPos));

        biome.decorate(this.world, this.rand, new BlockPos(i, 0, j));
        WorldEntitySpawner.performWorldGenSpawning(this.world, biome, i + 8, j + 8, 16, 16, this.rand);

        BlockFalling.fallInstantly = false;
    }

    @Override
    public boolean generateStructures(@Nonnull Chunk chunkIn, int x, int z)
    {
        return false;
    }
    @Nonnull
    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(@Nonnull EnumCreatureType creatureType, @Nonnull BlockPos pos)
    {
        Biome biome = this.world.getBiome(pos);
        return biome.getSpawnableList(creatureType);
    }
    @Nullable
    @Override
    public BlockPos getNearestStructurePos(@Nonnull World worldIn, @Nonnull String structureName, @Nonnull BlockPos position, boolean findUnexplored)
    {
        if (ConfigHandler.generateDungeons && largeDungeonGenerator.getStructureName().equals(structureName))
            return largeDungeonGenerator.getNearestStructurePos(worldIn, position, findUnexplored);

        return null;
    }
    @Override
    public void recreateStructures(@Nonnull Chunk chunkIn, int x, int z)
    {
        if (ConfigHandler.generateDungeons)
            largeDungeonGenerator.generate(world, x, z, null);
    }
    @Override
    public boolean isInsideStructure(@Nonnull World worldIn, @Nonnull String structureName, @Nonnull BlockPos pos)
    {
        if (ConfigHandler.generateDungeons)
        {
            if (largeDungeonGenerator != null && largeDungeonGenerator.getStructureName().equals(structureName))
                return largeDungeonGenerator.isInsideStructure(pos);
        }

        return false;
    }
}