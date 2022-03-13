package com.fi0x.deepmagic.world.dimensions;

import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

public interface ICustomChunkGenerator extends IChunkGenerator
{
    void setBlocksInChunk(int x, int z, ChunkPrimer primer);
}
