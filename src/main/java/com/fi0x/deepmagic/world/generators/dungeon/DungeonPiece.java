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
    public int posX;
    public int posY;
    public int posZ;
    public final int sizeX;
    public final int sizeY;
    public final int sizeZ;
    public int entranceHeightNorth;
    public int entranceHeightEast;
    public int entranceHeightSouth;
    public int entranceHeightWest;

    public Rotation rotation;

    public DungeonPiece(String name, int sizeX, int sizeY, int sizeZ, int entranceHeightNorth, int entranceHeightEast, int entranceHeightSouth, int entranceHeightWest)
    {
        this.templateName = name;

        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;

        this.entranceHeightNorth = entranceHeightNorth;
        this.entranceHeightEast = entranceHeightEast;
        this.entranceHeightSouth = entranceHeightSouth;
        this.entranceHeightWest = entranceHeightWest;

        this.rotation = Rotation.NONE;
    }

    @Override
    public boolean generate(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos pos)
    {
        return GenerationHelper.templatePlacer(world, rand, pos, templateName, rotation);
    }
}