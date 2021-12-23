package com.fi0x.deepmagic.world.generators.dungeon;

import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import com.fi0x.deepmagic.world.CustomTemplateProcessor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.common.IWorldGenerator;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SmallDungeon extends WorldGenerator implements IWorldGenerator
{
    private final DungeonRoom[] rooms;
    private final DungeonRoom ceiling;

    public SmallDungeon()
    {
        ArrayList<IBlockState> replaceableBlocks = new ArrayList<>();
        replaceableBlocks.add(ModBlocks.DUNGEON_BRICK.getDefaultState());

        Map<Float, Template.BlockInfo> replacementBlocks = new HashMap<>();
        replacementBlocks.put(0.05F, CustomTemplateProcessor.blockToInfo(ModBlocks.DUNGEON_COBBLE_MOSSY.getDefaultState()));
        replacementBlocks.put(0.1F, CustomTemplateProcessor.blockToInfo(ModBlocks.DUNGEON_COBBLE.getDefaultState()));
        replacementBlocks.put(0.15F, CustomTemplateProcessor.blockToInfo(ModBlocks.DUNGEON_STONE.getDefaultState()));
        replacementBlocks.put(0.25F, CustomTemplateProcessor.blockToInfo(ModBlocks.DUNGEON_BRICK_CRACKED.getDefaultState()));
        replacementBlocks.put(0.3F, CustomTemplateProcessor.blockToInfo(ModBlocks.DUNGEON_BRICK_MOSSY.getDefaultState()));

        rooms = new DungeonRoom[]{
                new DungeonRoom("small_dungeon_room0", 7, replaceableBlocks, replacementBlocks),
                new DungeonRoom("small_dungeon_room1", 6, replaceableBlocks, replacementBlocks),
                new DungeonRoom("small_dungeon_room2", 6, replaceableBlocks, replacementBlocks),
                new DungeonRoom("small_dungeon_room3", 8, replaceableBlocks, replacementBlocks),
                new DungeonRoom("small_dungeon_room4", 7, replaceableBlocks, replacementBlocks),
                new DungeonRoom("small_dungeon_room5", 6, replaceableBlocks, replacementBlocks)};
        ceiling = new DungeonRoom("small_dungeon_ceiling", 1, replaceableBlocks, replacementBlocks);

    }

    @Override
    public boolean generate(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos position)
    {
        int maxHeight = 60 + rand.nextInt(10);
        if(world.provider.getDimension() == ConfigHandler.dimensionIdDepthID) maxHeight = 100 + rand.nextInt(20);

        while(position.getY() < maxHeight)
        {
            int room = rand.nextInt(rooms.length);

            rooms[room].generate(world, rand, position);
            position = position.add(0, rooms[room].height, 0);
        }
        ceiling.generate(world, rand, position);

        return true;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
    }
}