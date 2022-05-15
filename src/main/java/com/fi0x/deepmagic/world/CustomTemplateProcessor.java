package com.fi0x.deepmagic.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.Template;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class CustomTemplateProcessor implements ITemplateProcessor
{
    private Random rand;
    private final ArrayList<IBlockState> originalBlocks;
    private final Map<Float, Template.BlockInfo> alternativeBlocks;

    public CustomTemplateProcessor(ArrayList<IBlockState> replaceableBlocks, Map<Float, Template.BlockInfo> replacementBlocks)
    {
        originalBlocks = replaceableBlocks;
        alternativeBlocks = replacementBlocks;
    }

    @Nullable
    @Override
    public Template.BlockInfo processBlock(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Template.BlockInfo blockInfoIn)
    {
        if(originalBlocks.contains(blockInfoIn.blockState))
        {
            for (Map.Entry<Float, Template.BlockInfo> entry : alternativeBlocks.entrySet())
            {
                if(rand.nextFloat() > entry.getKey())
                    continue;

                return entry.getValue();
            }
        }

        return blockInfoIn;
    }

    public void setRandom(Random random)
    {
        rand = random;
    }

    public static Template.BlockInfo blockToInfo(IBlockState block)
    {
        return new Template.BlockInfo(BlockPos.ORIGIN, block, null);
    }
}