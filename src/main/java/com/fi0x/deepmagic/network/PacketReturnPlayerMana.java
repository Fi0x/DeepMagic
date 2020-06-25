package com.fi0x.deepmagic.network;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.gui.GuiManaRenderOverlay;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketReturnPlayerMana implements IMessage
{
    private boolean messageValid;

    private double currentMana;

    public PacketReturnPlayerMana()
    {
        this.messageValid = false;
    }
    public PacketReturnPlayerMana(double currentMana)
    {
        this.currentMana = currentMana;

        messageValid = true;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        try
        {
            currentMana = buf.readDouble();
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
    }

    public static class Handler implements IMessageHandler<PacketReturnPlayerMana, IMessage>
    {
        @Override
        public IMessage onMessage(PacketReturnPlayerMana message, MessageContext ctx)
        {
            if(!message.messageValid && ctx.side != Side.CLIENT) return null;
            Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message));
            return null;
        }

        void processMessage(PacketReturnPlayerMana message)
        {
            try
            {
                GuiManaRenderOverlay.instance.setCurrentMana(message.currentMana);
            } catch(Exception e)
            {
                Main.getLogger().catching(e);
            }
        }
    }
}