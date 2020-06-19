package com.fi0x.deepmagic.network;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.gui.GuiManaRenderOverlay;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketReturnManaAmount implements IMessage
{
    private boolean messageValid;
    private double currentMana;
    private double maxMana;

    public PacketReturnManaAmount()
    {
        this.messageValid = false;
    }
    public PacketReturnManaAmount(double currentMana, double maxMana)
    {
        this.currentMana = currentMana;
        this.maxMana = maxMana;
        messageValid = true;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        try
        {
            currentMana = buf.readDouble();
            maxMana = buf.readDouble();
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
                GuiManaRenderOverlay.instance.setValues(message.currentMana, message.maxMana);
            } catch(Exception e)
            {
                Main.getLogger().catching(e);
            }
        }
    }
}