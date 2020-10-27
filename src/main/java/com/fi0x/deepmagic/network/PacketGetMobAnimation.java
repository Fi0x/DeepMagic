package com.fi0x.deepmagic.network;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.entities.mobs.EntityDepthMage;
import com.fi0x.deepmagic.entities.mobs.EntityRockTroll;
import com.fi0x.deepmagic.util.handlers.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketGetMobAnimation implements IMessage
{
    private boolean messageValid;
    private int entityID;
    private int dimension;
    private int attackState;
    private int defenceState;

    public PacketGetMobAnimation()
    {
        messageValid = false;
    }
    public PacketGetMobAnimation(int entityID, int dimension, int attackState, int defenceState)
    {
        this.entityID = entityID;
        this.dimension = dimension;
        this.attackState = attackState;
        this.defenceState = defenceState;

        messageValid = true;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        try
        {
            entityID = buf.readInt();
            dimension = buf.readInt();
            attackState = buf.readInt();
            defenceState = buf.readInt();
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
        buf.writeInt(entityID);
        buf.writeInt(dimension);
        buf.writeInt(attackState);
        buf.writeInt(defenceState);
    }

    public static class Handler implements IMessageHandler<PacketGetMobAnimation, IMessage>
    {
        @Override
        public IMessage onMessage(PacketGetMobAnimation message, MessageContext ctx)
        {
            if(!message.messageValid && ctx.side != Side.SERVER) return null;
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));
            return null;
        }

        void processMessage(PacketGetMobAnimation message, MessageContext ctx)
        {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension);
            Entity entity = world.getEntityByID(message.entityID);
            if(entity instanceof EntityDepthMage)
            {
                PacketHandler.INSTANCE.sendTo(new PacketReturnMobAnimation(message.entityID, ((EntityDepthMage) entity).attackTimer), ctx.getServerHandler().player);
            } else
            if(entity instanceof EntityRockTroll)
            {
                PacketHandler.INSTANCE.sendTo(new PacketReturnMobAnimation(message.entityID, ((EntityRockTroll) entity).defenceTime), ctx.getServerHandler().player);
            }
        }
    }
}