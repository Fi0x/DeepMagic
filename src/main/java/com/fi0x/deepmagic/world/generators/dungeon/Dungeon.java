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
    private boolean hasOpenEndings;

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

        dungeonCore.generate(world, rand, position);
        dungeonRooms.add(dungeonCore);
        hasOpenEndings = true;

        while(hasOpenEndings)
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
            while(corridor.entranceHeightSouth == -1)
            {
                corridor.rotate90Deg();
            }

            BlockPos placementPosition = new BlockPos(currentRoom.pos.getX(), currentRoom.pos.getY() + currentRoom.entranceHeightNorth - corridor.entranceHeightSouth, currentRoom.pos.getZ() - Math.abs(currentRoom.offsetZ) - Math.abs(corridor.offsetZ) - 1);
            corridor.generate(world, rand, placementPosition);

            //TODO: select random new room
            //TODO: create room
            //TODO: add room to room-list
        }

        if(currentRoom.entranceHeightEast >= 0)
        {
            DungeonPiece corridor = corridorType[rand.nextInt(corridorType.length)];
            while(corridor.entranceHeightWest == -1)
            {
                corridor.rotate90Deg();
            }

            BlockPos placementPosition = new BlockPos(currentRoom.pos.getX() + Math.abs(currentRoom.offsetX) + Math.abs(corridor.offsetX) + 1, currentRoom.pos.getY() + currentRoom.entranceHeightEast - corridor.entranceHeightWest, currentRoom.pos.getZ());
            corridor.generate(world, rand, placementPosition);

            //TODO: select random new room
            //TODO: create room
            //TODO: add room to room-list
        }

        if(currentRoom.entranceHeightSouth >= 0)
        {
            DungeonPiece corridor = corridorType[rand.nextInt(corridorType.length)];
            while(corridor.entranceHeightNorth == -1)
            {
                corridor.rotate90Deg();
            }

            BlockPos placementPosition = new BlockPos(currentRoom.pos.getX(), currentRoom.pos.getY() + currentRoom.entranceHeightNorth - corridor.entranceHeightSouth, currentRoom.pos.getZ() + Math.abs(currentRoom.offsetZ) + Math.abs(corridor.offsetZ) + 1);
            corridor.generate(world, rand, placementPosition);

            //TODO: select random new room
            //TODO: create room
            //TODO: add room to room-list
        }

        if(currentRoom.entranceHeightWest >= 0)
        {
            DungeonPiece corridor = corridorType[rand.nextInt(corridorType.length)];
            while(corridor.entranceHeightEast == -1)
            {
                corridor.rotate90Deg();
            }

            BlockPos placementPosition = new BlockPos(currentRoom.pos.getX() - Math.abs(currentRoom.offsetX) - Math.abs(corridor.offsetX) - 1, currentRoom.pos.getY() + currentRoom.entranceHeightWest - corridor.entranceHeightEast, currentRoom.pos.getZ());
            corridor.generate(world, rand, placementPosition);

            //TODO: select random new room
            //TODO: create room
            //TODO: add room to room-list
        }

        dungeonRooms.remove(0);
        if(dungeonRooms.isEmpty()) hasOpenEndings = false;
    }



    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
    }
}