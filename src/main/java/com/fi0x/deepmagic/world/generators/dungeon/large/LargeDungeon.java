package com.fi0x.deepmagic.world.generators.dungeon.large;

import com.fi0x.deepmagic.init.BiomeInit;
import com.fi0x.deepmagic.world.dimensions.ICustomChunkGenerator;
import com.google.common.collect.ImmutableList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.structure.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LargeDungeon extends MapGenStructure
{
    private static final String STRUCTURE_NAME = "DMLargeDungeon";
    private static final int SPACING = 10;
    private static final int MIN_SPACING = 1;

    protected static final ImmutableList<Biome> BIOMES = ImmutableList.of(
            BiomeInit.DEPTH, BiomeInit.INSANITY_PLAINS, BiomeInit.INSANITY_HILLS, BiomeInit.INSANITY_FOREST_SMALL,
            BiomeInit.INSANITY_FOREST_MIXED, BiomeInit.INSANITY_FOREST_LARGE);
    private final ICustomChunkGenerator generator;

    static
    {
        MapGenStructureIO.registerStructure(Start.class, STRUCTURE_NAME);
        LargeDungeonComponentPlacer.register();
    }

    public LargeDungeon(ICustomChunkGenerator providerIn)
    {
        this.generator = providerIn;
    }

    @Nonnull
    @Override
    public String getStructureName()
    {
        return STRUCTURE_NAME;
    }

    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        int x = chunkX;
        int z = chunkZ;

        if (x < 0)
            x -= SPACING;

        if (z < 0)
            z -= SPACING;

        int randX = x / SPACING;
        int randZ = z / SPACING;
        Random random = this.world.setRandomSeed(randX, randZ, 10387319);
        randX *= SPACING;
        randZ *= SPACING;
        randX += (random.nextInt(SPACING / 2 - 1) + random.nextInt(SPACING / 2 - 1)) / 2;
        randZ += (random.nextInt(SPACING / 2 - 1) + random.nextInt(SPACING / 2 - 1)) / 2;

        if (chunkX == randX && chunkZ == randZ)
            return world.provider.getBiomeProvider().areBiomesViable(chunkX * 16 + 8, chunkZ * 16 + 8, 32, BIOMES);

        return false;
    }

    @Override
    public BlockPos getNearestStructurePos(World worldIn, @Nullable BlockPos pos, boolean findUnexplored)
    {
        this.world = worldIn;
        BiomeProvider biomeprovider = worldIn.getBiomeProvider();
        if(biomeprovider.isFixedBiome() && !BIOMES.contains(biomeprovider.getFixedBiome()))
            return null;

        assert pos != null;
        return findNearestStructurePosBySpacing(worldIn, this, pos, SPACING, MIN_SPACING, 10387319, true, 100, findUnexplored);
    }

    @Nonnull
    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        return new Start(this.world, this.generator, this.rand, chunkX, chunkZ);
    }

    public static class Start extends StructureStart
    {
        private boolean isValid;

        public Start()
        {
            super();
        }

        public Start(World world, ICustomChunkGenerator generator, Random random, int chunkX, int chunkZ)
        {
            super(chunkX, chunkZ);

            Rotation rot = Rotation.values()[random.nextInt(Rotation.values().length)];
            ChunkPrimer primer = new ChunkPrimer();
            generator.setBlocksInChunk(chunkX, chunkZ, primer);

            int x = 5, z = 5;
            if (rot == Rotation.CLOCKWISE_90)
                x = -5;
            else if (rot == Rotation.CLOCKWISE_180)
            {
                x = -5;
                z = -5;
            }
            else if (rot == Rotation.COUNTERCLOCKWISE_90)
                z = -5;

            int height1 = primer.findGroundBlockIdx(7, 7);
            int height2 = primer.findGroundBlockIdx(7, 7 + z);
            int height3 = primer.findGroundBlockIdx(7 + x, 7);
            int height4 = primer.findGroundBlockIdx(7 + x, 7 + z);
            int minHeight = Math.min(Math.min(height1, height2), Math.min(height3, height4));

            if (minHeight >= 2)
            {
                BlockPos pos = new BlockPos(chunkX * 16 + 8, minHeight + 1, chunkZ * 16 + 8);
                List<LargeDungeonComponent> pieces = new ArrayList<>();
                LargeDungeonComponentPlacer.generate(world.getSaveHandler().getStructureTemplateManager(), pos, rot, random, pieces);
                components.addAll(pieces);
                updateBoundingBox();
                isValid = true;
            }
        }

        @Override
        public void generateStructure(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull StructureBoundingBox structurebb)
        {
            super.generateStructure(worldIn, rand, structurebb);

            for (StructureComponent component : components)
            {
                if (component instanceof LargeDungeonComponent)
                    ((LargeDungeonComponent) component).onPostGeneration(worldIn, structurebb);
            }
        }

        public boolean isSizeableStructure()
        {
            return this.isValid;
        }

        @Override
        public void writeToNBT(@Nonnull NBTTagCompound tagCompound)
        {
            super.writeToNBT(tagCompound);
            tagCompound.setBoolean("valid", isValid);
        }
        @Override
        public void readFromNBT(@Nonnull NBTTagCompound tagCompound)
        {
            super.readFromNBT(tagCompound);
            isValid = tagCompound.getBoolean("valid");
        }
    }
}
