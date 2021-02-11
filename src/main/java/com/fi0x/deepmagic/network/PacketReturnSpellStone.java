package com.fi0x.deepmagic.network;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.gui.GuiManaRenderOverlay;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketReturnSpellStone implements IMessage
{
    private boolean messageValid;

    private double pressedButton;

    public PacketReturnSpellStone()
    {
        this.messageValid = false;
    }
    public PacketReturnSpellStone(double pressedButton)
    {
        this.pressedButton = pressedButton;

        messageValid = true;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        try
        {
            pressedButton = buf.readDouble();
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
        buf.writeDouble(pressedButton);
    }

    public static class Handler implements IMessageHandler<PacketReturnSpellStone, IMessage>
    {
        @Override
        public IMessage onMessage(PacketReturnSpellStone message, MessageContext ctx)
        {
            if(!message.messageValid && ctx.side != Side.SERVER) return null;
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));
            return null;
        }

        void processMessage(PacketReturnSpellStone message)
        {
            try
            {
                GuiManaRenderOverlay.instance.setCurrentMana(message.pressedButton);
            } catch(Exception e)
            {
                Main.getLogger().catching(e);
            }
        }
    }
}