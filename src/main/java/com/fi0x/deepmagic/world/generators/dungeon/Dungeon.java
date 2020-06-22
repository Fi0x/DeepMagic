package com.fi0x.deepmagic.world.generators.dungeon;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.Random;

public class Dungeon extends WorldGenerator implements IWorldGenerator
{
    private BlockPos dungeonStartPos;
    private boolean hasOpenEndings;

    private final DungeonPiece dungeonCore = new DungeonPiece("dungeon_entrance", 21, 9, 21, 1, 1, 1, 1, false);

    private DungeonPiece[] corridorType = new DungeonPiece[] {
            new DungeonPiece("dungeon_corridor_short", 3, 5, 5, -1, 1, -1, 1),
            new DungeonPiece("dungeon_corridor_long", 11, 5, 5, -1, 1, -1, 1)};

    private DungeonPiece[] roomType = new DungeonPiece[] {
            new DungeonPiece("dungeon_room1", 11, 6, 11, 1, 1, 1, 1),
            new DungeonPiece("dungeon_room2", 7, 5, 7, 1, -1, -1, -1),
            new DungeonPiece("dungeon_room3", 9, 6, 9, 1, -1, -1, -1),
            new DungeonPiece("dungeon_room4", 5, 5, 5, 1, -1, -1, -1),
            new DungeonPiece("dungeon_room5", 7, 6, 7, 1, -1, -1, -1),
            new DungeonPiece("dungeon_room6", 15, 8, 15, 1, -1, 1, -1)};

    private ArrayList<DungeonPiece> dungeonRooms = new ArrayList<>();

    @Override
    public boolean generate(World world, Random rand, BlockPos position)
    {
        dungeonStartPos = position;

        dungeonCore.generate(world, rand, dungeonStartPos);
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
            //TODO: select random corridor
            //TODO: create corridor
            //TODO: select random new room
            //TODO: create room
            //TODO: add room to roomlist
        }

        if(currentRoom.entranceHeightEast >= 0)
        {
            //TODO: select random corridor
            //TODO: create corridor
            //TODO: select random new room
            //TODO: create room
            //TODO: add room to roomlist
        }

        if(currentRoom.entranceHeightSouth >= 0)
        {
            //TODO: select random corridor
            //TODO: create corridor
            //TODO: select random new room
            //TODO: create room
            //TODO: add room to roomlist
        }

        if(currentRoom.entranceHeightWest >= 0)
        {
            //TODO: select random corridor
            //TODO: create corridor
            //TODO: select random new room
            //TODO: create room
            //TODO: add room to roomlist
        }

        dungeonRooms.remove(0);
        if(dungeonRooms.isEmpty()) hasOpenEndings = false;
    }



    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
    }
}