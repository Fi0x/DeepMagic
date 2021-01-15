package com.fi0x.deepmagic.world.dimensions.depth;

import com.fi0x.deepmagic.init.BiomeInit;
import com.fi0x.deepmagic.init.ModBlocks;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCavesHell;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenFire;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;
import net.minecraft.world.gen.feature.WorldGenGlowStone2;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextHell;
import net.minecraftforge.event.terraingen.TerrainGen;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ChunkGeneratorDepthOld implements IChunkGenerator
{
    //TODO: Check which blocks to use
    protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
    protected static final IBlockState DEPTH_STONE = ModBlocks.DEPTH_STONE.getDefaultState();
    protected static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
    protected static final IBlockState LAVA = Blocks.WATER.getDefaultState();
    protected static final IBlockState GRAVEL = ModBlocks.DEPTH_DIRT.getDefaultState();
    protected static final IBlockState SOUL_SAND = ModBlocks.DEPTH_DIRT.getDefaultState();
    private final World world;
    private final Random rand;
    private double[] slowsandNoise = new double[256];
    private double[] gravelNoise = new double[256];
    private double[] depthBuffer = new double[256];
    private double[] buffer;
    private NoiseGeneratorOctaves lperlinNoise1;
    private NoiseGeneratorOctaves lperlinNoise2;
    private NoiseGeneratorOctaves perlinNoise1;
    private NoiseGeneratorOctaves slowsandGravelNoiseGen;
    private NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
    public NoiseGeneratorOctaves scaleNoise;
    public NoiseGeneratorOctaves depthNoise;
    private final WorldGenFire fireFeature = new WorldGenFire();//TODO: Use ice instead of fire
    private final WorldGenGlowStone1 lightGemGen = new WorldGenGlowStone1();//TODO: Use custom glowstones
    private final WorldGenGlowStone2 hellPortalGen = new WorldGenGlowStone2();//TODO: Use custom glowstones
    private MapGenBase genNetherCaves = new MapGenCavesHell();//TODO: Change to use custom cave generator that uses space above 128
    double[] pnr;
    double[] ar;
    double[] br;
    double[] noiseData4;
    double[] dr;

    public ChunkGeneratorDepthOld(World worldIn, long seed)
    {
        //TODO: Set correct and necessary values
        this.world = worldIn;
        this.rand = new Random(seed);
        this.lperlinNoise1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.lperlinNoise2 = new NoiseGeneratorOctaves(this.rand, 16);
        this.perlinNoise1 = new NoiseGeneratorOctaves(this.rand, 8);
        this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.rand, 4);
        this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.rand, 4);
        this.scaleNoise = new NoiseGeneratorOctaves(this.rand, 10);
        this.depthNoise = new NoiseGeneratorOctaves(this.rand, 16);
        worldIn.setSeaLevel(63);

        ContextHell ctx = new ContextHell(lperlinNoise1, lperlinNoise2, perlinNoise1, slowsandGravelNoiseGen, netherrackExculsivityNoiseGen, scaleNoise, depthNoise);
        ctx = TerrainGen.getModdedNoiseGenerators(worldIn, this.rand, ctx);
        this.lperlinNoise1 = ctx.getLPerlin1();
        this.lperlinNoise2 = ctx.getLPerlin2();
        this.perlinNoise1 = ctx.getPerlin();
        this.slowsandGravelNoiseGen = ctx.getPerlin2();
        this.netherrackExculsivityNoiseGen = ctx.getPerlin3();
        this.scaleNoise = ctx.getScale();
        this.depthNoise = ctx.getDepth();
        this.genNetherCaves = TerrainGen.getModdedMapGen(genNetherCaves, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.NETHER_CAVE);
    }

    @Nonnull
    @Override
    public Chunk generateChunk(int x, int z)
    {
        //TODO: Generate chunk correctly
        this.rand.setSeed((long) x * 341873128712L + (long) z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        this.prepareHeights(x, z, chunkprimer);
        this.buildSurfaces(x, z, chunkprimer);
        this.genNetherCaves.generate(this.world, x, z, chunkprimer);

        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        byte[] abyte = chunk.getBiomeArray();

        for(int i = 0; i < abyte.length; ++i)
        {
            abyte[i] = (byte) Biome.getIdForBiome(BiomeInit.DEPTH);
        }

        chunk.resetRelightChecks();
        return chunk;
    }

    public void prepareHeights(int p_185936_1_, int p_185936_2_, ChunkPrimer primer)
    {
        //TODO: Set correct heights
        int j = this.world.getSeaLevel() / 2 + 1;
        this.buffer = this.getHeights(this.buffer, p_185936_1_ * 4, 0, p_185936_2_ * 4, 5, 17, 5);

        for(int j1 = 0; j1 < 4; ++j1)
        {
            for(int k1 = 0; k1 < 4; ++k1)
            {
                for(int l1 = 0; l1 < 16; ++l1)
                {
                    double d1 = this.buffer[((j1) * 5 + k1) * 17 + l1];
                    double d2 = this.buffer[((j1) * 5 + k1 + 1) * 17 + l1];
                    double d3 = this.buffer[((j1 + 1) * 5 + k1) * 17 + l1];
                    double d4 = this.buffer[((j1 + 1) * 5 + k1 + 1) * 17 + l1];
                    double d5 = (this.buffer[((j1) * 5 + k1) * 17 + l1 + 1] - d1) * 0.125D;
                    double d6 = (this.buffer[((j1) * 5 + k1 + 1) * 17 + l1 + 1] - d2) * 0.125D;
                    double d7 = (this.buffer[((j1 + 1) * 5 + k1) * 17 + l1 + 1] - d3) * 0.125D;
                    double d8 = (this.buffer[((j1 + 1) * 5 + k1 + 1) * 17 + l1 + 1] - d4) * 0.125D;

                    for(int i2 = 0; i2 < 8; ++i2)
                    {
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * 0.25D;
                        double d13 = (d4 - d2) * 0.25D;

                        for(int j2 = 0; j2 < 4; ++j2)
                        {
                            double d15 = d10;
                            double d16 = (d11 - d10) * 0.25D;

                            for(int k2 = 0; k2 < 4; ++k2)
                            {
                                IBlockState iblockstate = null;

                                if(l1 * 8 + i2 < j) iblockstate = LAVA;

                                if(d15 > 0.0D) iblockstate = DEPTH_STONE;

                                int l2 = j2 + j1 * 4;
                                int i3 = i2 + l1 * 8;
                                int j3 = k2 + k1 * 4;
                                assert iblockstate != null;
                                primer.setBlockState(l2, i3, j3, iblockstate);
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    private double[] getHeights(double[] dArr, int num1, int num2, int num3, int num4, int num5, int num6)
    {
        //TODO: Check if heights are correct
        if(dArr == null)
        {
            dArr = new double[num4 * num5 * num6];
        }

        net.minecraftforge.event.terraingen.ChunkGeneratorEvent.InitNoiseField event = new net.minecraftforge.event.terraingen.ChunkGeneratorEvent.InitNoiseField(this, dArr, num1, num2, num3, num4, num5, num6);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
        if(event.getResult() == net.minecraftforge.fml.common.eventhandler.Event.Result.DENY) return event.getNoisefield();

        this.noiseData4 = this.scaleNoise.generateNoiseOctaves(this.noiseData4, num1, num2, num3, num4, 1, num6, 1.0D, 0.0D, 1.0D);
        this.dr = this.depthNoise.generateNoiseOctaves(this.dr, num1, num2, num3, num4, 1, num6, 100.0D, 0.0D, 100.0D);
        this.pnr = this.perlinNoise1.generateNoiseOctaves(this.pnr, num1, num2, num3, num4, num5, num6, 8.555150000000001D, 34.2206D, 8.555150000000001D);
        this.ar = this.lperlinNoise1.generateNoiseOctaves(this.ar, num1, num2, num3, num4, num5, num6, 684.412D, 2053.236D, 684.412D);
        this.br = this.lperlinNoise2.generateNoiseOctaves(this.br, num1, num2, num3, num4, num5, num6, 684.412D, 2053.236D, 684.412D);
        int i = 0;
        double[] adouble = new double[num5];

        for(int j = 0; j < num5; ++j)
        {
            adouble[j] = Math.cos((double) j * Math.PI * 6.0D / (double) num5) * 2.0D;
            double d2 = j;

            if(j > num5 / 2)
            {
                d2 = (num5 - 1 - j);
            }

            if(d2 < 4.0D)
            {
                d2 = 4.0D - d2;
                adouble[j] -= d2 * d2 * d2 * 10.0D;
            }
        }

        for(int l = 0; l < num4; ++l)
        {
            for(int i1 = 0; i1 < num6; ++i1)
            {
                for(int k = 0; k < num5; ++k)
                {
                    double d4 = adouble[k];
                    double d5 = this.ar[i] / 512.0D;
                    double d6 = this.br[i] / 512.0D;
                    double d7 = (this.pnr[i] / 10.0D + 1.0D) / 2.0D;
                    double d8;

                    if(d7 < 0.0D)
                    {
                        d8 = d5;
                    } else if(d7 > 1.0D)
                    {
                        d8 = d6;
                    } else
                    {
                        d8 = d5 + (d6 - d5) * d7;
                    }

                    d8 = d8 - d4;

                    if(k > num5 - 4)
                    {
                        double d9 = ((float) (k - (num5 - 4)) / 3.0F);
                        d8 = d8 * (1.0D - d9) + -10.0D * d9;
                    }

                    if((double) k < 0.0D)
                    {
                        double d10 = (0.0D - (double) k) / 4.0D;
                        d10 = MathHelper.clamp(d10, 0.0D, 1.0D);
                        d8 = d8 * (1.0D - d10) + -10.0D * d10;
                    }

                    dArr[i] = d8;
                    ++i;
                }
            }
        }

        return dArr;
    }

    public void buildSurfaces(int chunkX, int chunkZ, ChunkPrimer primer)
    {
        //TODO: Find out what method does
        if(!net.minecraftforge.event.ForgeEventFactory.onReplaceBiomeBlocks(this, chunkX, chunkZ, primer, this.world)) return;
        int oceanHeight = this.world.getSeaLevel() + 1;
        this.slowsandNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.slowsandNoise, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, 0.03125D, 0.03125D, 1.0D);
        this.gravelNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.gravelNoise, chunkX * 16, 109, chunkZ * 16, 16, 1, 16, 0.03125D, 1.0D, 0.03125D);
        this.depthBuffer = this.netherrackExculsivityNoiseGen.generateNoiseOctaves(this.depthBuffer, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, 0.0625D, 0.0625D, 0.0625D);

        for(int blockZ = 0; blockZ < 16; ++blockZ)
        {
            for(int blockX = 0; blockX < 16; ++blockX)
            {
                boolean flagSoulSand = this.slowsandNoise[blockZ + blockX * 16] + this.rand.nextDouble() * 0.2D > 0.0D;
                boolean flagGravel = this.gravelNoise[blockZ + blockX * 16] + this.rand.nextDouble() * 0.2D > 0.0D;
                int depthLayout = (int) (this.depthBuffer[blockZ + blockX * 16] / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);
                int i1 = -1;
                IBlockState iblockstate = DEPTH_STONE;
                IBlockState iblockstate1 = DEPTH_STONE;

                for(int currentY = 255; currentY >= 0; --currentY)
                {
                    if(currentY < 255 - this.rand.nextInt(5) && currentY > this.rand.nextInt(5))
                    {
                        IBlockState currentBlockState = primer.getBlockState(blockX, currentY, blockZ);

                        currentBlockState.getBlock();
                        if(currentBlockState.getMaterial() != Material.AIR)
                        {
                            if(currentBlockState.getBlock() == ModBlocks.DEPTH_STONE)
                            {
                                if(i1 == -1)
                                {
                                    if(depthLayout <= 0)
                                    {
                                        iblockstate = AIR;
                                        iblockstate1 = DEPTH_STONE;
                                    } else if(currentY >= oceanHeight - 4 && currentY <= oceanHeight + 1)
                                    {
                                        iblockstate = DEPTH_STONE;
                                        iblockstate1 = DEPTH_STONE;

                                        if(flagGravel)
                                        {
                                            iblockstate = GRAVEL;
                                            iblockstate1 = DEPTH_STONE;
                                        }

                                        if(flagSoulSand)
                                        {
                                            iblockstate = SOUL_SAND;
                                            iblockstate1 = SOUL_SAND;
                                        }
                                    }

                                    if(currentY < oceanHeight && iblockstate.getMaterial() == Material.AIR) iblockstate = LAVA;

                                    i1 = depthLayout;

                                    if(currentY >= oceanHeight - 1) primer.setBlockState(blockX, currentY, blockZ, iblockstate);
                                    else primer.setBlockState(blockX, currentY, blockZ, iblockstate1);

                                } else if(i1 > 0)
                                {
                                    --i1;
                                    primer.setBlockState(blockX, currentY, blockZ, iblockstate1);
                                }
                            }
                        } else i1 = -1;
                    } else primer.setBlockState(blockX, currentY, blockZ, BEDROCK);
                }
            }
        }
    }

    public void populate(int x, int z)
    {
        /*
        TODO: Compare with insanity and hell generators
        TODO: Generate frozen lava
          Generate custom plants
          Generate custom Glowstone
          Generate alternative to fire
         */
        BlockFalling.fallInstantly = true;
        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, false);
        int i = x * 16;
        int j = z * 16;
        BlockPos blockpos = new BlockPos(i, 0, j);
        Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));
        ChunkPos chunkpos = new ChunkPos(x, z);

        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, false);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.terraingen.DecorateBiomeEvent.Pre(this.world, this.rand, chunkpos));

        biome.decorate(this.world, this.rand, new BlockPos(i, 0, j));

        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.terraingen.DecorateBiomeEvent.Post(this.world, this.rand, blockpos));

        BlockFalling.fallInstantly = false;
    }

    public boolean generateStructures(@Nonnull Chunk chunkIn, int x, int z)
    {
        return false;
    }

    @Nonnull
    public List<Biome.SpawnListEntry> getPossibleCreatures(@Nonnull EnumCreatureType creatureType, @Nonnull BlockPos pos)
    {
        Biome biome = this.world.getBiome(pos);
        return biome.getSpawnableList(creatureType);
    }

    @Nullable
    public BlockPos getNearestStructurePos(@Nonnull World worldIn, @Nonnull String structureName, @Nonnull BlockPos position, boolean findUnexplored)
    {
        return null;
    }

    public boolean isInsideStructure(@Nonnull World worldIn, @Nonnull String structureName, @Nonnull BlockPos pos)
    {
        return false;
    }

    public void recreateStructures(@Nonnull Chunk chunkIn, int x, int z)
    {
    }
}