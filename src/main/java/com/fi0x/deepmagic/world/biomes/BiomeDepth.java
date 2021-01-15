package com.fi0x.deepmagic.world.biomes;

import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nonnull;

public class BiomeDepth extends Biome
{
    private static final Block TOP_BLOCK = ModBlocks.DEPTH_STONE;
    private static final Block FILLER_BLOCK = ModBlocks.DEPTH_STONE;
    private static final int SKY_COLOR = MathHelper.hsvToRGB(0.1F, 0F, 0F);
    private static final int FOLIAGE_COLOR = MathHelper.hsvToRGB(0.33F, 0.48F, 0.22F);

    public BiomeDepth()
    {
        super(new BiomeProperties("Depth").setBaseHeight(0.05F).setHeightVariation(0.001F).setTemperature(0.1F).setWaterColor(7094447));//TODO: Adjust watercolor
        topBlock = TOP_BLOCK.getDefaultState();
        fillerBlock = FILLER_BLOCK.getDefaultState();

        this.spawnableCaveCreatureList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();

        //TODO: Add spawn-lists

        this.flowers.clear();
        //TODO: use custom flowers

        decorator = new DecoratorDepthBiome();
    }

    @Override
    public int getSkyColorByTemp(float currentTemperature)
    {
        return SKY_COLOR;
    }
    @Override
    public boolean canRain()
    {
        return false;
    }
    @Override
    public int getFoliageColorAtPos(@Nonnull BlockPos pos)
    {
        return FOLIAGE_COLOR;
    }
}