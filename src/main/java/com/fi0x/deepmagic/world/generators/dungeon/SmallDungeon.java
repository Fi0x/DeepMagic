package com.fi0x.deepmagic.world.generators.dungeon;

import com.fi0x.deepmagic.util.handlers.ConfigHandler;
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
    private final DungeonRoom[] rooms = new DungeonRoom[]{
            new DungeonRoom("small_dungeon_room0", 7),
            new DungeonRoom("small_dungeon_room1", 6),
            new DungeonRoom("small_dungeon_room2", 6),
            new DungeonRoom("small_dungeon_room3", 8),
            new DungeonRoom("small_dungeon_room4", 7),
            new DungeonRoom("small_dungeon_room5", 6)};
    private final DungeonRoom ceiling = new DungeonRoom("small_dungeon_ceiling", 1);

    @Override
    public boolean generate(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos position)
    {
        int maxHeight = 60 + rand.nextInt(10);
        if(world.provider.getDimension() == ConfigHandler.dimensionIdDepthID) maxHeight = 200 + rand.nextInt(40);

        while(position.getY() < maxHeight)
        {
            int room = rand.nextInt(rooms.length);

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