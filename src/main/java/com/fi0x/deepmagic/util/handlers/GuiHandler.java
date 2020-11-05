package com.fi0x.deepmagic.util.handlers;

import com.fi0x.deepmagic.blocks.containers.ContainerManaGenerator;
import com.fi0x.deepmagic.blocks.containers.ContainerManaInfuser;
import com.fi0x.deepmagic.blocks.tileentity.TileEntityManaGenerator;
import com.fi0x.deepmagic.blocks.tileentity.TileEntityManaInfuser;
import com.fi0x.deepmagic.gui.GuiManaGenerator;
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
        if(ID == ConfigHandler.guiManaGeneratorID) return new ContainerManaGenerator(player.inventory, (TileEntityManaGenerator) world.getTileEntity(new BlockPos(x, y, z)));
        if(ID == ConfigHandler.guiManaInfuserID) return new ContainerManaInfuser(player.inventory, (TileEntityManaInfuser) world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(ID == ConfigHandler.guiManaGeneratorID) return new GuiManaGenerator(player.inventory, (TileEntityManaGenerator) world.getTileEntity(new BlockPos(x, y, z)));
        if(ID == ConfigHandler.guiManaInfuserID) return new GuiManaInfuser(player.inventory, (TileEntityManaInfuser) world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }
}