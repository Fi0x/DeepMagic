package com.fi0x.deepmagic.network;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.blocks.rituals.tile.TileEntityRitualQuarry;
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

public class PacketGetRitualQuarry implements IMessage
{
    private boolean messageValid;
    private int dimension;
    private BlockPos blockPos;

    public PacketGetRitualQuarry()
    {
        messageValid = false;
    }
    public PacketGetRitualQuarry(int dimension, BlockPos position)
    {
        this.dimension = dimension;
        this.blockPos = position;

        messageValid = true;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        try
        {
            dimension = buf.readInt();
            blockPos = BlockPos.fromLong(buf.readLong());
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
    }

    public static class Handler implements IMessageHandler<PacketGetRitualQuarry, IMessage>
    {

        @Override
        public IMessage onMessage(PacketGetRitualQuarry message, MessageContext ctx)
        {
            if(!message.messageValid && ctx.side != Side.SERVER) return null;
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));
            return null;
        }

        void processMessage(PacketGetRitualQuarry message, MessageContext ctx)
        {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension);
            TileEntity te = world.getTileEntity(message.blockPos);
            if(te instanceof TileEntityRitualQuarry)
            {
                PacketHandler.INSTANCE.sendTo(new PacketReturnRitualQuarry(message.dimension, message.blockPos, ((TileEntityRitualQuarry) te).getPacketParts()), ctx.getServerHandler().player);
            }
        }
    }
}