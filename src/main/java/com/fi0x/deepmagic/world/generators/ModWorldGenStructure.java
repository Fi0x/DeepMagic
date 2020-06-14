package com.fi0x.deepmagic.world.generators;

import com.fi0x.deepmagic.util.IStructure;
import com.fi0x.deepmagic.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class ModWorldGenStructure extends WorldGenerator implements IStructure
{
	public String structureName;
	
	public ModWorldGenStructure(String name)
	{
		structureName = name;
	}
	
	@Override
	public boolean generate(World world, @Nonnull Random rand, @Nonnull BlockPos pos)
	{
		Template template = ((WorldServer) world).getStructureTemplateManager().get(world.getMinecraftServer(), new ResourceLocation(Reference.MOD_ID, structureName));

		if(template != null)
		{
			Rotation rotation = Rotation.values()[rand.nextInt(4)];
			PlacementSettings settings = new PlacementSettings().setMirror(Mirror.NONE).setRotation(rotation).setIgnoreStructureBlock(false);
			template.addBlocksToWorld(world, pos, settings);

			Map<BlockPos, String> dataBlocks = template.getDataBlocks(pos, settings);
			for(Entry<BlockPos, String> entry : dataBlocks.entrySet())
			{
				try
				{
					String[] data = entry.getValue().split(" ");
					if(data.length < 2) continue;
					Block block = Block.getBlockFromName(data[0]);
					assert block != null;
					IBlockState state = block.getDefaultState();
					for(Entry<IProperty<?>, Comparable<?>> entry2 : block.getDefaultState().getProperties().entrySet())
					{
						if(entry2.getKey().getValueClass().equals(EnumFacing.class) && entry2.getKey().getName().equals("facing"))
						{
							state = state.withRotation(rotation.add(Rotation.CLOCKWISE_180));
							break;
						}
					}
					world.setBlockState(entry.getKey(), state, 3);
					TileEntity te = world.getTileEntity(entry.getKey());
					if(te == null) continue;
					if(te instanceof TileEntityLockableLoot) ((TileEntityLockableLoot) te).setLootTable(new ResourceLocation(data[1]), rand.nextLong());
				} catch (Exception ignored) { }
			}

//			IBlockState state = world.getBlockState(pos);
//			world.notifyBlockUpdate(pos, state, state, 3);
//			template.addBlocksToWorldChunk(world, pos, settings);
			return true;
		}
		return false;
	}
}