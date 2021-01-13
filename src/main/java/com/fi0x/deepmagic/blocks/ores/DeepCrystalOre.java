package com.fi0x.deepmagic.blocks.ores;

import com.fi0x.deepmagic.blocks.BlockBase;
import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.particlesystem.ParticleEnum;
import com.fi0x.deepmagic.particlesystem.ParticleSpawner;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public class DeepCrystalOre extends BlockBase
{
	public DeepCrystalOre(String name, Material material)
	{
		super(name, material);
		setSoundType(SoundType.STONE);
		setHardness(3.0F);
		setResistance(5.0F);
		setHarvestLevel("pickaxe", 2);
	}

	@Nonnull
	@Override
	public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune)
	{
		return ModItems.DEEP_CRYSTAL_POWDER;
	}

	@Override
	public int quantityDropped(Random rand)
	{
		return rand.nextInt((4) + 1);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(@Nonnull IBlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand)
	{
		if(rand.nextInt(100) + 1 > ConfigHandler.deepCrystalOreParticles) return;

		int particles = 0;
		switch(Minecraft.getMinecraft().gameSettings.particleSetting)
		{
			case 0:
				particles = 1;
				break;
			case 1:
				particles = 2;
		}

		for(int i = 0; i < particles; i++)
		{
			double xCenter = pos.getX() + 0.5;
			double yCenter = pos.getY() + 0.5;
			double zCenter = pos.getZ() + 0.5;

			int side = rand.nextInt(6);
			double xOff = side % 6 == 2 ? -0.5 : (side % 6 == 3 ? 0.5 : (Math.random() - 0.5));
			double yOff = side % 6 == 0 ? -0.5 : (side % 6 == 1 ? 0.5 : (Math.random() - 0.5));
			double zOff = side % 6 == 4 ? -0.5 : (side % 6 == 5 ? 0.5 : (Math.random() - 0.5));

			ParticleSpawner.spawnParticle(ParticleEnum.DEEP_CRYSTAL_ORE, xCenter + xOff, yCenter + yOff, zCenter + zOff, xOff, yOff, zOff);
		}
	}
}