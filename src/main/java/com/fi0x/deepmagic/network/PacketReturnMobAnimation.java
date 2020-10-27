package com.fi0x.deepmagic.network;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.entities.mobs.EntityDepthMage;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketReturnMobAnimation implements IMessage
{
    private boolean messageValid;

    private int entityID;
    private int attackState;

    public PacketReturnMobAnimation()
    {
        this.messageValid = false;
    }
    public PacketReturnMobAnimation(int entityID, int attackState)
    {
        this.entityID = entityID;
        this.attackState = attackState;

        messageValid = true;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        try
        {
            entityID = buf.readInt();
            attackState = buf.readInt();
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
        buf.writeInt(attackState);
    }

    public static class Handler implements IMessageHandler<PacketReturnMobAnimation, IMessage>
    {
        @Override
        public IMessage onMessage(PacketReturnMobAnimation message, MessageContext ctx)
        {
            if(!message.messageValid && ctx.side != Side.CLIENT) return null;
            Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
            return null;
        }

        void processMessage(PacketReturnMobAnimation message)
        {
            try
            {
                Entity entity = Minecraft.getMinecraft().world.getEntityByID(message.entityID);
                if(entity instanceof EntityDepthMage)
                {
                    ((EntityDepthMage) entity).attackCounter = message.attackState;
                }
            } catch(Exception e)
            {
                Main.getLogger().catching(e);
            }
        }
    }
}