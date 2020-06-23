package com.fi0x.deepmagic.world.generators.dungeon;

import com.fi0x.deepmagic.util.IStructure;
import com.fi0x.deepmagic.world.GenerationHelper;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

public class DungeonPiece extends WorldGenerator implements IStructure
{
    private final String templateName;

    public BlockPos pos;

    private int sizeX;
    public int sizeY;
    private int sizeZ;
    public int offsetX;
    public int offsetZ;

    public int entranceHeightNorth;
    public int entranceHeightEast;
    public int entranceHeightSouth;
    public int entranceHeightWest;

    private final boolean requiresEntrance;
    public Rotation rotation;

    public DungeonPiece(String name, int sizeX, int sizeY, int sizeZ, int entranceHeightNorth, int entranceHeightEast, int entranceHeightSouth, int entranceHeightWest)
    {
        this.templateName = name;

        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        offsetX = - sizeX / 2;
        offsetZ = - sizeZ / 2;

        this.entranceHeightNorth = entranceHeightNorth;
        this.entranceHeightEast = entranceHeightEast;
        this.entranceHeightSouth = entranceHeightSouth;
        this.entranceHeightWest = entranceHeightWest;

        this.requiresEntrance = false;
        this.rotation = Rotation.NONE;
    }
    public DungeonPiece(String name, int sizeX, int sizeY, int sizeZ, int entranceHeightNorth, int entranceHeightEast, int entranceHeightSouth, int entranceHeightWest, boolean isEntrance)
    {
        this.templateName = name;

        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        offsetX = - sizeX / 2;
        offsetZ = - sizeZ / 2;

        this.entranceHeightNorth = entranceHeightNorth;
        this.entranceHeightEast = entranceHeightEast;
        this.entranceHeightSouth = entranceHeightSouth;
        this.entranceHeightWest = entranceHeightWest;

        this.requiresEntrance = isEntrance;
        this.rotation = Rotation.NONE;
    }

    @Override
    public boolean generate(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos pos)
    {
        this.pos = pos;
        if(requiresEntrance)
        {
            digTunnelToSurface();
        }
        return GenerationHelper.templatePlacer(world, rand, this.pos.add(offsetX, 0, offsetZ), templateName, rotation);
    }
    public void rotate90Deg()
    {
        rotation = rotation.add(Rotation.CLOCKWISE_90);

        int temp = entranceHeightNorth;
        entranceHeightNorth = entranceHeightWest;
        entranceHeightWest = entranceHeightSouth;
        entranceHeightSouth = entranceHeightEast;
        entranceHeightEast = temp;

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

    private void digTunnelToSurface()
    {
        //TODO: Create tunnel to surface
    }
}