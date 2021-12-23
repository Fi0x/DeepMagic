package com.fi0x.deepmagic.world.generators.dungeon;

import com.fi0x.deepmagic.util.IStructure;
import com.fi0x.deepmagic.world.CustomTemplateProcessor;
import com.fi0x.deepmagic.world.GenerationHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.Template;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class DungeonRoom extends WorldGenerator implements IStructure
{
    private final String templateName;

    public final int height;
    private final CustomTemplateProcessor processor;

    public DungeonRoom(String name, int height, ArrayList<IBlockState> replaceableBlocks, Map<Float, Template.BlockInfo> replacementBlocks)
    {
        this.templateName = name;
        this.height = height;
        processor = new CustomTemplateProcessor(replaceableBlocks, replacementBlocks);
    }

    @Override
    public boolean generate(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos pos)
    {
        processor.setRandom(rand);
        return GenerationHelper.templatePlacer(world, rand, pos, templateName, Rotation.NONE, processor);
    }
}