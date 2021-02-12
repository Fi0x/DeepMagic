package com.fi0x.deepmagic.network;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.blocks.mana.tile.TileEntitySpellStone;
import com.fi0x.deepmagic.util.handlers.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketInformGuiChange implements IMessage
{
    private boolean messageValid;
    private int dimension;
    private BlockPos blockPos;
    private int pressedButton;

    public PacketInformGuiChange()
    {
        messageValid = false;
    }
    public PacketInformGuiChange(int dimension, BlockPos position, int pressedButton)
    {
        this.dimension = dimension;
        this.blockPos = position;
        this.pressedButton = pressedButton;

        messageValid = true;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        try
        {
            dimension = buf.readInt();
            blockPos = BlockPos.fromLong(buf.readLong());
            pressedButton = buf.readInt();
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
        buf.writeInt(pressedButton);
    }

    public static class Handler implements IMessageHandler<PacketInformGuiChange, IMessage>
    {
        @Override
        public IMessage onMessage(PacketInformGuiChange message, MessageContext ctx)
        {
            if(!message.messageValid && ctx.side != Side.SERVER) return null;
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));
            return null;
        }

        void processMessage(PacketInformGuiChange message, MessageContext ctx)
        {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension);
            TileEntity te = world.getTileEntity(message.blockPos);
            if(te instanceof TileEntitySpellStone)
            {
                ((TileEntitySpellStone) te).setField(2, message.pressedButton);
                PacketHandler.INSTANCE.sendTo(new PacketReturnSpellStone(message.dimension, message.blockPos, ((TileEntitySpellStone) te).getPacketParts()), ctx.getServerHandler().player);
            }
        }
    }
}