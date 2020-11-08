package com.fi0x.deepmagic.util.handlers;

import com.fi0x.deepmagic.blocks.containers.*;
import com.fi0x.deepmagic.blocks.tileentity.*;
import com.fi0x.deepmagic.gui.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler
{
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(ID == ConfigHandler.guiManaAltarID) return new ContainerManaAltar(player.inventory, (TileEntityManaAltar) world.getTileEntity(new BlockPos(x, y, z)));
        if(ID == ConfigHandler.guiManaGeneratorNormalID) return new ContainerManaGeneratorNormal(player.inventory, (TileEntityManaGeneratorNormal) world.getTileEntity(new BlockPos(x, y, z)));
        if(ID == ConfigHandler.guiManaGeneratorInsanityID) return new ContainerManaGeneratorInsanity(player.inventory, (TileEntityManaGeneratorInsanity) world.getTileEntity(new BlockPos(x, y, z)));
        if(ID == ConfigHandler.guiManaGeneratorMobID) return new ContainerManaGeneratorMob(player.inventory, (TileEntityManaGeneratorMob) world.getTileEntity(new BlockPos(x, y, z)));
        if(ID == ConfigHandler.guiManaInfuserID) return new ContainerManaInfuser(player.inventory, (TileEntityManaInfuser) world.getTileEntity(new BlockPos(x, y, z)));
        if(ID == ConfigHandler.guiManaGrinderID) return new ContainerManaGrinder(player.inventory, (TileEntityManaGrinder) world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(ID == ConfigHandler.guiManaAltarID) return new GuiManaAltar(player.inventory, (TileEntityManaAltar) world.getTileEntity(new BlockPos(x, y, z)));
        if(ID == ConfigHandler.guiManaGeneratorNormalID) return new GuiManaGeneratorNormal(player.inventory, (TileEntityManaGeneratorNormal) world.getTileEntity(new BlockPos(x, y, z)));
        if(ID == ConfigHandler.guiManaGeneratorInsanityID) return new GuiManaGeneratorInsanity(player.inventory, (TileEntityManaGeneratorInsanity) world.getTileEntity(new BlockPos(x, y, z)));
        if(ID == ConfigHandler.guiManaGeneratorMobID) return new GuiManaGeneratorMob(player.inventory, (TileEntityManaGeneratorMob) world.getTileEntity(new BlockPos(x, y, z)));
        if(ID == ConfigHandler.guiManaInfuserID) return new GuiManaInfuser(player.inventory, (TileEntityManaInfuser) world.getTileEntity(new BlockPos(x, y, z)));
        if(ID == ConfigHandler.guiManaGrinderID) return new GuiManaGrinder(player.inventory, (TileEntityManaGrinder) world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }
}