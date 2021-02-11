package com.fi0x.deepmagic.network;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import com.fi0x.deepmagic.util.handlers.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketGetSpellStone implements IMessage
{
    private boolean messageValid;
    private BlockPos blockPos;
    private int pressedButton;

    public PacketGetSpellStone()
    {
        messageValid = false;
    }
    public PacketGetSpellStone(BlockPos position, int pressedButton)
    {
        this.blockPos = position;
        this.pressedButton = pressedButton;

        messageValid = true;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        try
        {
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
        buf.writeLong(blockPos.toLong());
        buf.writeInt(pressedButton);
    }

    public static class Handler implements IMessageHandler<PacketGetSpellStone, IMessage>
    {
        @Override
        public IMessage onMessage(PacketGetSpellStone message, MessageContext ctx)
        {
            if(!message.messageValid && ctx.side != Side.CLIENT) return null;
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));
            Minecraft.getMinecraft().addScheduledTask(() -> processMessage(message, ctx));
            return null;
        }

        void processMessage(PacketGetSpellStone message, MessageContext ctx)
        {
            EntityPlayer player = ctx.getServerHandler().player.getServerWorld().getPlayerEntityByName(message.playerName);
            if(player != null && player.hasCapability(PlayerProperties.PLAYER_MANA, null))
            {
                PlayerMana playerMana = player.getCapability(PlayerProperties.PLAYER_MANA, null);
                assert playerMana != null;
                PacketHandler.INSTANCE.sendTo(new PacketReturnPlayerMana(playerMana.getMana()), ctx.getServerHandler().player);
            }
        }
    }
}