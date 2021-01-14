package com.fi0x.deepmagic.world.dimensions.depth;

import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ChunkGeneratorDepth implements IChunkGenerator
{
    private final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
    private final IBlockState FILLER_MAIN = ModBlocks.DEPTH_STONE.getDefaultState();

    private final int MAX_HEIGHT = 255;
    private final World world;
    private final Random rand;

    public ChunkGeneratorDepth(World worldIn, long seed)
    {
        //TODO: Set correct and necessary values
        this.world = worldIn;
        this.rand = new Random(seed);
        worldIn.setSeaLevel(63);
    }
    @Nonnull
    @Override
    public Chunk generateChunk(int x, int z)
    {
        ChunkPrimer primer = new ChunkPrimer();
        fillChunk(primer);

        Chunk chunk = new Chunk(world, primer, x, z);
        return chunk;
    }
    private void fillChunk(ChunkPrimer primer)
    {
        for(int x = 0; x < 16; x++)
        {
            for(int z = 0; z < 16; z++)
            {
                for(int y = 255; y >= 0; y--)
                {
                    if(rand.nextInt(5) >= y) primer.setBlockState(x, y, z, BEDROCK);
                    else if(MAX_HEIGHT - rand.nextInt(5) <= y) primer.setBlockState(x, y, z, BEDROCK);
                    else primer.setBlockState(x, y, z, FILLER_MAIN);
                }
            }
        }
    }

    @Override
    public void populate(int x, int z)
    {
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
        return null;
    }
    @Override
    public void recreateStructures(@Nonnull Chunk chunkIn, int x, int z)
    {
    }
    @Override
    public boolean isInsideStructure(@Nonnull World worldIn, @Nonnull String structureName, @Nonnull BlockPos pos)
    {
        return false;
    }
}
