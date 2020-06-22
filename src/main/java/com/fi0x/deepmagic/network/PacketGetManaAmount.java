package com.fi0x.deepmagic.network;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketGetManaAmount implements IMessage
{
    private boolean messageValid;
    private String playerName;

    private double currentMana;
    private double maxMana;

    public PacketGetManaAmount()
    {
        messageValid = false;
    }
    public PacketGetManaAmount(String playerName, double currentMana, double maxMana)
    {
        this.playerName = playerName;
        this.currentMana = currentMana;
        this.maxMana = maxMana;
        messageValid = true;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        try
        {
            playerName = ByteBufUtils.readUTF8String(buf);
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
        ByteBufUtils.writeUTF8String(buf, playerName);
        buf.writeDouble(currentMana);
        buf.writeDouble(maxMana);
    }

    public static class Handler implements IMessageHandler<PacketGetManaAmount, IMessage>
    {

        @Override
        public IMessage onMessage(PacketGetManaAmount message, MessageContext ctx)
        {
            if(!message.messageValid && ctx.side != Side.SERVER) return null;
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));
            return null;
        }

        void processMessage(PacketGetManaAmount message, MessageContext ctx)
        {
            EntityPlayer player = ctx.getServerHandler().player.getServerWorld().getPlayerEntityByName(message.playerName);
            if(player != null && player.hasCapability(PlayerProperties.PLAYER_MANA, null))
            {
                PlayerMana playerMana = player.getCapability(PlayerProperties.PLAYER_MANA, null);
                assert playerMana != null;
                PacketHandler.INSTANCE.sendTo(new PacketReturnManaAmount(playerMana.getMana(), playerMana.getMaxMana()), ctx.getServerHandler().player);
            }
        }
    }
}