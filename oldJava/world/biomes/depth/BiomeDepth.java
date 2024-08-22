package com.fi0x.deepmagic.world.biomes.depth;

import com.fi0x.deepmagic.entities.mobs.EntityDepthMage;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nonnull;

public class BiomeDepth extends Biome
{
    private static final Block TOP_BLOCK = ModBlocks.DEPTH_STONE;
    private static final Block FILLER_BLOCK = ModBlocks.DEPTH_STONE;
    private static final int SKY_COLOR = 0;
    private static final int FOLIAGE_COLOR = 3681309;

    public BiomeDepth()
    {
        super(new BiomeProperties("Depth").setBaseHeight(0.05F).setHeightVariation(0.001F).setTemperature(0.1F).setWaterColor(69));
        topBlock = TOP_BLOCK.getDefaultState();
        fillerBlock = FILLER_BLOCK.getDefaultState();

        this.spawnableCaveCreatureList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();

        if(ConfigHandler.allowDepthMage) this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityDepthMage.class, 15, 1, 2));

        this.flowers.clear();
        addFlower(ModBlocks.DEPTH_FLOWER.getDefaultState(), 20);

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