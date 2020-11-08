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
    public final int height;
    public int offsetX;
    public int offsetZ;

    public Rotation rotation;

    public SmallDungeonRoom(String name, int sizeX, int sizeZ, int height)
    {
        this.templateName = name;

        this.sizeX = sizeX;
        this.sizeZ = sizeZ;
        this.height = height;

        offsetX = - sizeX / 2;
        offsetZ = - sizeZ / 2;

        this.rotation = Rotation.NONE;
    }

    @Override
    public boolean generate(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos pos)
    {
        this.pos = pos;
        return GenerationHelper.templatePlacer(world, rand, this.pos.add(offsetX, 0, offsetZ), templateName, rotation);
    }
}