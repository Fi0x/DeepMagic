package com.fi0x.deepmagic.world.generators.dungeon;

import com.fi0x.deepmagic.util.Reference;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.Random;

public class Dungeon implements IWorldGenerator
{
    private final DungeonPiece dungeonCore = new DungeonPiece("dungeon_entrance", 21, 9, 21, 1, 1, 1, 1);

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

    private ArrayList<DungeonPiece> dungeonRoom= new ArrayList<>();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        boolean hasOpenEndings = true;

        if(world.provider.getDimension() != Reference.DIMENSION_ID_INSANITY) return;
        if(chunkX % 100 != 0 || chunkZ % 100 != 0) return;

        //TODO: generate dungeon
    }
}