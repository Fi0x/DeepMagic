package com.fi0x.deepmagic.blocks.insanity;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.init.DeepMagicTab;
import com.fi0x.deepmagic.init.ModBlocks;
import com.fi0x.deepmagic.init.ModItems;
import com.fi0x.deepmagic.particlesystem.ParticleEnum;
import com.fi0x.deepmagic.particlesystem.ParticleSpawner;
import com.fi0x.deepmagic.util.IHasModel;
import com.fi0x.deepmagic.util.handlers.ConfigHandler;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Random;

public class InsanityFlower extends BlockBush implements IHasModel
{
	public InsanityFlower(String name, Material material)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(DeepMagicTab.BLOCKS);
		
		setSoundType(SoundType.PLANT);

		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(Objects.requireNonNull(this.getRegistryName())));
	}

	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(@Nonnull IBlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand)
	{
		if(rand.nextInt(100) + 1 > ConfigHandler.plantParticles) return;

		double x = pos.getX() + Math.random();
		double y = pos.getY() + Math.random();
		double z = pos.getZ() + Math.random();

		ParticleSpawner.spawnParticle(ParticleEnum.INSANITY_PLANT, new BlockPos(x, y, z));
	}
}