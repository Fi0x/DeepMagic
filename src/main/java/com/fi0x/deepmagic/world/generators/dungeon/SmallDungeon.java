package com.fi0x.deepmagic.world.generators.dungeon;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

public class SmallDungeon extends WorldGenerator implements IWorldGenerator
{
    private final SmallDungeonRoom[] rooms = new SmallDungeonRoom[] {
            new SmallDungeonRoom("small_dungeon_room0", 16, 16, 7),
            new SmallDungeonRoom("small_dungeon_room1", 16, 16, 6)};

    @Override
    public boolean generate(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos position)
    {
        int maxHeight = 60 + (int) (Math.random() * 15);
        int previousRoom = 0;

        while (position.getY() < maxHeight)
        {
            int room = (int) (Math.random() * rooms.length);
            if(room == previousRoom) room++;
            if(room == rooms.length) room = 0;

            rooms[room].generate(world, rand, position);
            position = position.add(0, rooms[room].height, 0);
        }

        return true;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
    }
}