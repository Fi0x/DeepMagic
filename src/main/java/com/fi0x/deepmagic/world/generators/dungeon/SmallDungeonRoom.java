package com.fi0x.deepmagic.world.generators.dungeon;

import com.fi0x.deepmagic.util.IStructure;
import com.fi0x.deepmagic.world.GenerationHelper;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

public class SmallDungeonRoom extends WorldGenerator implements IStructure
{
    private final String templateName;

    public BlockPos pos;

    private final int sizeX;
    private final int sizeZ;
    public int offsetX;
    public int offsetZ;

    public boolean hasEntranceNorth;
    public boolean hasEntranceEast;
    public boolean hasEntranceSouth;
    public boolean hasEntranceWest;

    public Rotation rotation;

    public SmallDungeonRoom(String name, boolean hasEntranceNorth, boolean hasEntranceEast, boolean hasEntranceSouth, boolean hasEntranceWest)
    {
        this.templateName = name;

        this.sizeX = 16;
        this.sizeZ = 16;
        offsetX = - sizeX / 2;
        offsetZ = - sizeZ / 2;

        this.hasEntranceNorth = hasEntranceNorth;
        this.hasEntranceEast = hasEntranceEast;
        this.hasEntranceSouth = hasEntranceSouth;
        this.hasEntranceWest = hasEntranceWest;

        this.rotation = Rotation.NONE;
    }

    @Override
    public boolean generate(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos pos)
    {
        this.pos = pos;
        return GenerationHelper.templatePlacer(world, rand, this.pos.add(offsetX, 0, offsetZ), templateName, rotation);
    }

    public void rotate90Deg()
    {
        rotation = rotation.add(Rotation.CLOCKWISE_90);

        boolean temp = hasEntranceNorth;
        hasEntranceNorth = hasEntranceWest;
        hasEntranceWest = hasEntranceSouth;
        hasEntranceSouth = hasEntranceEast;
        hasEntranceEast = temp;

        switch(rotation)
        {
            case NONE:
                offsetX = - sizeX / 2;
                offsetZ = - sizeZ / 2;
                break;
            case CLOCKWISE_90:
                offsetX = sizeZ / 2;
                offsetZ = - sizeX / 2;
                break;
            case CLOCKWISE_180:
                offsetX = sizeX / 2;
                offsetZ = sizeZ / 2;
                break;
            case COUNTERCLOCKWISE_90:
                offsetX = - sizeZ / 2;
                offsetZ = sizeX / 2;
                break;
        }
    }
}