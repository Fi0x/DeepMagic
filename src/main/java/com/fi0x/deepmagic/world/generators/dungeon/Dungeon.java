package com.fi0x.deepmagic.world.generators.dungeon;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Random;

public class Dungeon extends WorldGenerator implements IWorldGenerator
{
    private BlockPos dungeonCenter;

    private final DungeonPiece dungeonCore = new DungeonPiece("dungeon_entrance", 21, 9, 21, 1, 1, 1, 1, false);
    private final DungeonPiece[] corridorType = new DungeonPiece[] {
            new DungeonPiece("dungeon_corridor_short", 3, 5, 5, -1, 1, -1, 1),
            new DungeonPiece("dungeon_corridor_long", 11, 5, 5, -1, 1, -1, 1)};
    private final DungeonPiece[] roomType = new DungeonPiece[] {
            new DungeonPiece("dungeon_room1", 11, 6, 11, 1, 1, 1, 1),
            new DungeonPiece("dungeon_room2", 7, 5, 7, 1, -1, -1, -1),
            new DungeonPiece("dungeon_room3", 9, 6, 9, 1, -1, -1, -1),
            new DungeonPiece("dungeon_room4", 5, 5, 5, 1, -1, -1, -1),
            new DungeonPiece("dungeon_room5", 7, 6, 7, 1, -1, -1, -1),
            new DungeonPiece("dungeon_room6", 15, 8, 15, 1, -1, 1, -1)};

    private final ArrayList<DungeonPiece> dungeonRooms = new ArrayList<>();

    @Override
    public boolean generate(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos position)
    {
        dungeonCenter = position;
        dungeonCore.generate(world, rand, dungeonCenter);
        dungeonRooms.add(dungeonCore);

        while(!dungeonRooms.isEmpty())
        {
            generateDungeon(world, rand);
        }
        return true;
    }

    private void generateDungeon(World world, Random rand)
    {
        DungeonPiece currentRoom = dungeonRooms.get(0);
        if(currentRoom.entranceHeightNorth >= 0)
        {
            DungeonPiece corridor = corridorType[rand.nextInt(corridorType.length)];
            while(corridor.entranceHeightSouth == -1) corridor.rotate90Deg();

            int x = currentRoom.pos.getX();
            int y = currentRoom.pos.getY() + currentRoom.entranceHeightNorth - corridor.entranceHeightSouth;
            int z = currentRoom.pos.getZ() - Math.abs(currentRoom.offsetZ) - Math.abs(corridor.offsetZ) - 1;

            corridor.generate(world, rand, new BlockPos(x, y, z));

            DungeonPiece newRoom = roomType[rand.nextInt(roomType.length)];
            while(newRoom.entranceHeightSouth == -1) newRoom.rotate90Deg();

            x = corridor.pos.getX();
            y = corridor.pos.getY() + corridor.entranceHeightNorth - newRoom.entranceHeightSouth;
            z = corridor.pos.getZ() - Math.abs(corridor.offsetZ) - Math.abs(newRoom.offsetZ) - 1;

            newRoom.generate(world, rand, new BlockPos(x, y, z));

            newRoom.entranceHeightSouth = -1;
            if(newRoom.pos.getDistance(dungeonCenter.getX(), dungeonCenter.getY(), dungeonCenter.getZ()) < 100) dungeonRooms.add(newRoom);
        }

        if(currentRoom.entranceHeightEast >= 0)
        {
            DungeonPiece corridor = corridorType[rand.nextInt(corridorType.length)];
            while(corridor.entranceHeightWest == -1) corridor.rotate90Deg();

            int x = currentRoom.pos.getX() + Math.abs(currentRoom.offsetX) + Math.abs(corridor.offsetX) + 1;
            int y = currentRoom.pos.getY() + currentRoom.entranceHeightEast - corridor.entranceHeightWest;
            int z = currentRoom.pos.getZ();

            corridor.generate(world, rand, new BlockPos(x, y, z));

            DungeonPiece newRoom = roomType[rand.nextInt(roomType.length)];
            while(newRoom.entranceHeightWest == -1) newRoom.rotate90Deg();

            x = corridor.pos.getX() + Math.abs(corridor.offsetX) + Math.abs(newRoom.offsetX) + 1;
            y = corridor.pos.getY() + corridor.entranceHeightEast - newRoom.entranceHeightWest;
            z = corridor.pos.getZ();

            newRoom.generate(world, rand, new BlockPos(x, y, z));

            newRoom.entranceHeightWest = -1;
            if(newRoom.pos.getDistance(dungeonCenter.getX(), dungeonCenter.getY(), dungeonCenter.getZ()) < 100) dungeonRooms.add(newRoom);
        }

        if(currentRoom.entranceHeightSouth >= 0)
        {
            DungeonPiece corridor = corridorType[rand.nextInt(corridorType.length)];
            while(corridor.entranceHeightNorth == -1) corridor.rotate90Deg();

            int x = currentRoom.pos.getX();
            int y = currentRoom.pos.getY() + currentRoom.entranceHeightSouth - corridor.entranceHeightNorth;
            int z = currentRoom.pos.getZ() + Math.abs(currentRoom.offsetZ) + Math.abs(corridor.offsetZ) + 1;

            corridor.generate(world, rand, new BlockPos(x, y, z));

            DungeonPiece newRoom = roomType[rand.nextInt(roomType.length)];
            while(newRoom.entranceHeightNorth == -1) newRoom.rotate90Deg();

            x = corridor.pos.getX();
            y = corridor.pos.getY() + corridor.entranceHeightSouth - newRoom.entranceHeightNorth;
            z = corridor.pos.getZ() + Math.abs(corridor.offsetZ) + Math.abs(newRoom.offsetZ) + 1;

            newRoom.generate(world, rand, new BlockPos(x, y, z));

            newRoom.entranceHeightNorth = -1;
            if(newRoom.pos.getDistance(dungeonCenter.getX(), dungeonCenter.getY(), dungeonCenter.getZ()) < 100) dungeonRooms.add(newRoom);
        }

        if(currentRoom.entranceHeightWest >= 0)
        {
            DungeonPiece corridor = corridorType[rand.nextInt(corridorType.length)];
            while(corridor.entranceHeightEast == -1) corridor.rotate90Deg();

            int x = currentRoom.pos.getX() - Math.abs(currentRoom.offsetX) - Math.abs(corridor.offsetX) - 1;
            int y = currentRoom.pos.getY() + currentRoom.entranceHeightWest - corridor.entranceHeightEast;
            int z = currentRoom.pos.getZ();

            corridor.generate(world, rand, new BlockPos(x, y, z));

            DungeonPiece newRoom = roomType[rand.nextInt(roomType.length)];
            while(newRoom.entranceHeightEast == -1) newRoom.rotate90Deg();

            x = corridor.pos.getX() - Math.abs(corridor.offsetX) - Math.abs(newRoom.offsetX) - 1;
            y = corridor.pos.getY() + corridor.entranceHeightWest - newRoom.entranceHeightEast;
            z = corridor.pos.getZ();

            newRoom.generate(world, rand, new BlockPos(x, y, z));

            newRoom.entranceHeightEast = -1;
            if(newRoom.pos.getDistance(dungeonCenter.getX(), dungeonCenter.getY(), dungeonCenter.getZ()) < 100) dungeonRooms.add(newRoom);
        }

        dungeonRooms.remove(0);
    }



    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
    }
}