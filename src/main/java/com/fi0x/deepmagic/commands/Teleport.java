package com.fi0x.deepmagic.commands;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import java.util.Objects;

public class Teleport extends Teleporter
{
	private final WorldServer world;
	private final double x;
	private final double y;
	private final double z;
	
	public Teleport(WorldServer worldIn, double x, double y, double z)
	{
		super(worldIn);
		this.world = worldIn;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static void teleportToDimension(EntityPlayer player, int dimension, double x, double y, double z)
	{
		EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
		MinecraftServer server = player.getEntityWorld().getMinecraftServer();
		assert server != null;
		WorldServer worldServer = server.getWorld(dimension);

		Objects.requireNonNull(worldServer.getMinecraftServer()).getPlayerList().transferPlayerToDimension(entityPlayerMP, dimension, new Teleport(worldServer, x, y, z));
	}
	
	@Override
	public void placeInPortal(Entity entityIn, float rotationYaw)
	{
		this.world.getBlockState(new BlockPos((int)this.x, (int)this.y, (int)this.z));
		entityIn.setPosition(x, y, z);
		entityIn.motionX = 0F;
		entityIn.motionY = 0F;
		entityIn.motionZ = 0F;
	}
}