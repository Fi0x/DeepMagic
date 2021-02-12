package com.fi0x.deepmagic.network;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.blocks.mana.tile.TileEntitySpellStone;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketUpdateSpellStoneClient implements IMessage
{
    private boolean messageValid;
    private int dimension;
    private BlockPos blockPos;
    private String parts;

    public PacketUpdateSpellStoneClient()
    {
        messageValid = false;
    }
    public PacketUpdateSpellStoneClient(int dimension, BlockPos position, String partList)
    {
        this.dimension = dimension;
        this.blockPos = position;
        this.parts = partList;

        messageValid = true;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        try
        {
            dimension = buf.readInt();
            blockPos = BlockPos.fromLong(buf.readLong());
            parts = ByteBufUtils.readUTF8String(buf);
        } catch(IndexOutOfBoundsException exception)
        {
            Main.getLogger().catching(exception);
            return;
        }
        messageValid = true;
    }
    @Override
    public void toBytes(ByteBuf buf)
    {
        if(!messageValid) return;
        buf.writeInt(dimension);
        buf.writeLong(blockPos.toLong());
        ByteBufUtils.writeUTF8String(buf, parts);
    }

    public static class Handler implements IMessageHandler<PacketUpdateSpellStoneClient, IMessage>
    {
        @Override
        public IMessage onMessage(PacketUpdateSpellStoneClient message, MessageContext ctx)
        {
            if(!message.messageValid && ctx.side != Side.CLIENT) return null;
            Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
            return null;
        }

        void processMessage(PacketUpdateSpellStoneClient message)
        {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension);
            TileEntity te = world.getTileEntity(message.blockPos);
            if(te instanceof TileEntitySpellStone)
            {
                ((TileEntitySpellStone) te).setPartsFromPacket(message.parts);
            }
        }
    }
}