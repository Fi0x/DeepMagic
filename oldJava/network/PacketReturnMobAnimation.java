package com.fi0x.deepmagic.network;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.entities.mobs.EntityDepthMage;
import com.fi0x.deepmagic.entities.mobs.EntityRockTroll;
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
    private int defenceState;

    public PacketReturnMobAnimation()
    {
        this.messageValid = false;
    }
    public PacketReturnMobAnimation(int entityID, int attackDefenceState)
    {
        this.entityID = entityID;
        this.attackState = attackDefenceState;
        this.defenceState = attackDefenceState;

        messageValid = true;
    }
    public PacketReturnMobAnimation(int entityID, int attackState, int defenceState)
    {
        this.entityID = entityID;
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
        buf.writeInt(attackState);
        buf.writeInt(defenceState);
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
                    ((EntityDepthMage) entity).attackTimer = message.attackState;
                } else if(entity instanceof EntityRockTroll)
                {
                    ((EntityRockTroll) entity).defenceTime = message.defenceState;
                }
            } catch(Exception e)
            {
                Main.getLogger().catching(e);
            }
        }
    }
}