package com.fi0x.deepmagic.world;

import com.fi0x.deepmagic.util.CustomNameGenerator;
import com.fi0x.deepmagic.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

import java.util.Map;
import java.util.Random;

public class GenerationHelper
{
    public static boolean templatePlacer(World world, Random rand, BlockPos pos, String templateName, Rotation rotation)
    {
        Template template = ((WorldServer) world).getStructureTemplateManager().get(world.getMinecraftServer(), new ResourceLocation(Reference.MOD_ID, templateName));
        if(template == null) return false;

        PlacementSettings settings = new PlacementSettings().setMirror(Mirror.NONE).setRotation(rotation).setIgnoreStructureBlock(false);
        template.addBlocksToWorld(world, pos, settings);

        Map<BlockPos, String> dataBlocks = template.getDataBlocks(pos, settings);
        dataBlockReplacer(world, rand, rotation, dataBlocks);

        return true;
    }

    public static void dataBlockReplacer(World world, Random rand, Rotation structureRotation, Map<BlockPos, String> dataBlocks)
    {
        for(Map.Entry<BlockPos, String> entry : dataBlocks.entrySet())
        {
            try
            {
                String[] data = entry.getValue().split(" ");
                if(data.length < 1) continue;
                IBlockState state;
                if(data[0].equals("Spawner"))
                {
                    world.setBlockState(entry.getKey(), Blocks.MOB_SPAWNER.getDefaultState());
                    TileEntityMobSpawner spawner = (TileEntityMobSpawner) world.getTileEntity(entry.getKey());
                    assert spawner != null;
                    MobSpawnerBaseLogic logic = spawner.getSpawnerBaseLogic();
                    if(data.length > 2)
                    {
                        logic.setEntityId(new ResourceLocation(data[1], data[2]));
                    } else
                    {
                        logic.setEntityId(CustomNameGenerator.getRandomSpawnableMob());
                    }
                    spawner.update();
                } else if(data.length > 1)
                {
                    Block block = Block.getBlockFromName(data[0]);
                    assert block != null;
                    state = block.getDefaultState();
                    for(Map.Entry<IProperty<?>, Comparable<?>> entry2 : block.getDefaultState().getProperties().entrySet())
                    {
                        if(entry2.getKey().getValueClass().equals(EnumFacing.class) && entry2.getKey().getName().equals("facing"))
                        {
                            if(data.length > 2)
                            {
                                switch (data[2])
                                {
                                    case "0":
                                        state = state.withRotation(structureRotation.add(Rotation.CLOCKWISE_90));
                                        break;
                                    case "1":
                                        state = state.withRotation(structureRotation.add(Rotation.CLOCKWISE_180));
                                        break;
                                    case "2":
                                        state = state.withRotation(structureRotation.add(Rotation.COUNTERCLOCKWISE_90));
                                        break;
                                    case "3":
                                        state = state.withRotation(structureRotation.add(Rotation.NONE));
                                        break;
                                }
                            } else
                            {
                                state = state.withRotation(structureRotation.add(Rotation.CLOCKWISE_90));
                            }
                            break;
                        }
                    }
                    world.setBlockState(entry.getKey(), state, 3);
                    TileEntity te = world.getTileEntity(entry.getKey());
                    if(te == null) continue;
                    if(te instanceof TileEntityLockableLoot) ((TileEntityLockableLoot) te).setLootTable(new ResourceLocation(data[1]), rand.nextLong());
                }
            } catch (Exception ignored) { }
        }
    }
}