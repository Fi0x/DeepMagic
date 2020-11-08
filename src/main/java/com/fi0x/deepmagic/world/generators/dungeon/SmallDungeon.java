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
            new SmallDungeonRoom("small_dungeon_room1", 16, 16, 6),
            new SmallDungeonRoom("small_dungeon_room2", 16, 16, 6),
            new SmallDungeonRoom("small_dungeon_room3", 16, 16, 8),
            new SmallDungeonRoom("small_dungeon_room4", 16, 16, 7),
            new SmallDungeonRoom("small_dungeon_room5", 16, 16, 6)};
    private final SmallDungeonRoom ceiling = new SmallDungeonRoom("small_dungeon_ceiling", 16, 16, 1);

    @Override
    public boolean generate(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos position)
    {
        int maxHeight = 55 + (int) (Math.random() * 10);

        while (position.getY() < maxHeight)
        {
            int room = (int) (Math.random() * rooms.length);

            rooms[room].generate(world, rand, position);
            position = position.add(0, rooms[room].height, 0);
        }
        ceiling.generate(world, rand, position);

        return true;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
    }
}