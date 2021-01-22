package com.fi0x.deepmagic.world.generators.dungeon;

import com.fi0x.deepmagic.util.IStructure;
import com.fi0x.deepmagic.world.GenerationHelper;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

public class DungeonRoom extends WorldGenerator implements IStructure
{
    private final String templateName;

    public final int height;

    public DungeonRoom(String name, int height)
    {
        this.templateName = name;
        this.height = height;
    }

    @Override
    public boolean generate(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos pos)
    {
        return GenerationHelper.templatePlacer(world, rand, pos, templateName, Rotation.NONE);
    }
}