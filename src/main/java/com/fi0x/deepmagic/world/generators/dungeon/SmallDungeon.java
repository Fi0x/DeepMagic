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
    private BlockPos dungeonCenter;

    private final SmallDungeonRoom dungeonCore = new SmallDungeonRoom("small_dungeon_center", true, true, true, true);
    private final SmallDungeonRoom[] dungeonSide = new SmallDungeonRoom[] {
            new SmallDungeonRoom("small_dungeon_side_1", false, true, true, true),
            new SmallDungeonRoom("small_dungeon_side_2", false, false, true, true)};
    private final SmallDungeonRoom[] dungeonCorner = new SmallDungeonRoom[] {
            new SmallDungeonRoom("small_dungeon_corner_1", false, false, true, false)};

    @Override
    public boolean generate(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos position)
    {
        dungeonCenter = position;
        dungeonCore.generate(world, rand, dungeonCenter);

        System.out.println("Generating 1");
        generateSides(world, rand);
        System.out.println("Generating 2");
//        generateCorners(world, rand);

        return true;
    }

    private void generateSides(World world, Random rand)
    {
        SmallDungeonRoom side = dungeonSide[rand.nextInt(dungeonSide.length)];
        while(!side.hasEntranceSouth || (!side.hasEntranceWest && !side.hasEntranceEast)) side.rotate90Deg();

        int x = dungeonCenter.getX();
        int y = dungeonCenter.getY();
        int z = dungeonCenter.getZ();

        side.generate(world, rand, new BlockPos(x, y, z - 16));

        side = dungeonSide[rand.nextInt(dungeonSide.length)];
        while(!side.hasEntranceWest || (!side.hasEntranceNorth && !side.hasEntranceSouth)) side.rotate90Deg();

        side.generate(world, rand, new BlockPos(x + 16, y, z));

        side = dungeonSide[rand.nextInt(dungeonSide.length)];
        while(!side.hasEntranceNorth || (!side.hasEntranceEast && !side.hasEntranceWest)) side.rotate90Deg();

        side.generate(world, rand, new BlockPos(x, y, z + 16));

        side = dungeonSide[rand.nextInt(dungeonSide.length)];
        while(!side.hasEntranceEast || (!side.hasEntranceNorth && !side.hasEntranceSouth)) side.rotate90Deg();

        side.generate(world, rand, new BlockPos(x - 16, y, z));
    }

    private void generateCorners(World world, Random rand)
    {
        SmallDungeonRoom corner = dungeonCorner[rand.nextInt(dungeonCorner.length)];
        while(!corner.hasEntranceSouth || !corner.hasEntranceWest) corner.rotate90Deg();

        int x = dungeonCenter.getX();
        int y = dungeonCenter.getY();
        int z = dungeonCenter.getZ();

        corner.generate(world, rand, new BlockPos(x + 16, y, z - 16));

        corner = dungeonCorner[rand.nextInt(dungeonCorner.length)];
        while(!corner.hasEntranceNorth || !corner.hasEntranceWest) corner.rotate90Deg();

        corner.generate(world, rand, new BlockPos(x + 16, y, z + 16));

        corner = dungeonCorner[rand.nextInt(dungeonCorner.length)];
        while(!corner.hasEntranceNorth || !corner.hasEntranceEast) corner.rotate90Deg();

        corner.generate(world, rand, new BlockPos(x - 16, y, z + 16));

        corner = dungeonCorner[rand.nextInt(dungeonCorner.length)];
        while(!corner.hasEntranceEast || !corner.hasEntranceSouth) corner.rotate90Deg();

        corner.generate(world, rand, new BlockPos(x - 16, y, z - 16));
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
    }
}