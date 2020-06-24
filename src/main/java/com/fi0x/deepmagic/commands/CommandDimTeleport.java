package com.fi0x.deepmagic.commands;

import com.fi0x.deepmagic.util.Reference;
import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import java.util.List;

public class CommandDimTeleport extends CommandBase
{
	private final List<String> aliases = Lists.newArrayList(Reference.MOD_ID, "tp", "tpdim", "tpdimension");
	
	@Nonnull
	@Override
	public String getName()
	{
		return "tpdimension";
	}
	@Nonnull
	@Override
	public String getUsage(@Nonnull ICommandSender sender)
	{
		return "tpdimension <id>";
	}
	@Nonnull
	@Override
	public List<String> getAliases()
	{
		return aliases;
	}
	@Override
	public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender)
	{
		return true;
	}
	@Override
	public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) {
		if(args.length < 1) return;
		
		String s = args[0];
		int dimensionID = 0;
		try
		{
			dimensionID = Integer.parseInt(s);
		} catch(NumberFormatException e)
		{
			sender.sendMessage(new TextComponentString(TextFormatting.RED + "Dimension ID invalid!"));
		}
		if(sender instanceof EntityPlayer)
		{
			Teleport.teleportToDimension((EntityPlayer)sender, dimensionID, sender.getPosition().getX(), sender.getPosition().getY(), sender.getPosition().getZ());
		}
	}
}