package com.fi0x.deepmagic.network;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.mana.player.GuiManaRenderOverlay;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketReturnManaAmount implements IMessage
{
    private boolean messageValid;
    private double currentMana;
    private double maxMana;

    private String className;
    private String currentManaName;
    private String maxManaName;

    public PacketReturnManaAmount()
    {
        this.messageValid = false;
    }
    public PacketReturnManaAmount(double currentMana, double maxMana, String className, String currentManaName, String maxManaName)
    {
        this.currentMana = currentMana;
        this.maxMana = maxMana;
        this.className = className;
        this.currentManaName = currentManaName;
        this.maxManaName = maxManaName;
        messageValid = true;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        try
        {
            currentMana = buf.readDouble();
            maxMana = buf.readDouble();
            className = ByteBufUtils.readUTF8String(buf);
            currentManaName = ByteBufUtils.readUTF8String(buf);
            maxManaName = ByteBufUtils.readUTF8String(buf);
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
        buf.writeDouble(currentMana);
        buf.writeDouble(maxMana);
        ByteBufUtils.writeUTF8String(buf, className);
        ByteBufUtils.writeUTF8String(buf, currentManaName);
        ByteBufUtils.writeUTF8String(buf, maxManaName);
    }

    public static class Handler implements IMessageHandler<PacketReturnManaAmount, IMessage>
    {
        @Override
        public IMessage onMessage(PacketReturnManaAmount message, MessageContext ctx)
        {
            if(!message.messageValid && ctx.side != Side.CLIENT) return null;
            Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
            return null;
        }

        void processMessage(PacketReturnManaAmount message)
        {
            try
            {
                Class cla = Class.forName(message.className);
//                Field currentManaField = cla.getDeclaredField(message.currentManaName);
//                Field maxManaField = cla.getDeclaredField(message.maxManaName);
//                currentManaField.setDouble(cla, message.currentMana);
//                maxManaField.setDouble(cla, message.maxMana);
                GuiManaRenderOverlay.instance.setValues(message.currentMana, message.maxMana);
            } catch(Exception e)
            {
                Main.getLogger().catching(e);
            }
        }
    }
}