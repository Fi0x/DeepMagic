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

public class PacketGetPlayerMana implements IMessage
{
    private boolean messageValid;
    private String playerName;

    private double currentMana;
    private double maxMana;
    private int skillpoints;
    private double manaRegenRate;
    private double manaEfficiency;
    private int addedHP;
    private int hpRegeneration;

    public PacketGetPlayerMana()
    {
        messageValid = false;
    }
    public PacketGetPlayerMana(String playerName, double currentMana, double maxMana, int skillpoints, double manaRegenRate, double manaEfficiency, int addedHP, int hpRegeneration)
    {
        this.playerName = playerName;
        this.currentMana = currentMana;
        this.maxMana = maxMana;
        this.skillpoints = skillpoints;
        this.manaRegenRate = manaRegenRate;
        this.manaEfficiency = manaEfficiency;
        this.addedHP = addedHP;
        this.hpRegeneration = hpRegeneration;

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
            skillpoints = buf.readInt();
            manaRegenRate = buf.readDouble();
            manaEfficiency = buf.readDouble();
            addedHP = buf.readInt();
            hpRegeneration = buf.readInt();
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
        buf.writeInt(skillpoints);
        buf.writeDouble(manaRegenRate);
        buf.writeDouble(manaEfficiency);
        buf.writeInt(addedHP);
        buf.writeInt(hpRegeneration);
    }

    public static class Handler implements IMessageHandler<PacketGetPlayerMana, IMessage>
    {

        @Override
        public IMessage onMessage(PacketGetPlayerMana message, MessageContext ctx)
        {
            if(!message.messageValid && ctx.side != Side.SERVER) return null;
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));
            return null;
        }

        void processMessage(PacketGetPlayerMana message, MessageContext ctx)
        {
            EntityPlayer player = ctx.getServerHandler().player.getServerWorld().getPlayerEntityByName(message.playerName);
            if(player != null && player.hasCapability(PlayerProperties.PLAYER_MANA, null))
            {
                PlayerMana playerMana = player.getCapability(PlayerProperties.PLAYER_MANA, null);
                assert playerMana != null;
                PacketHandler.INSTANCE.sendTo(new PacketReturnPlayerMana(playerMana.getMana(), playerMana.getMaxMana(), playerMana.getSkillpoints(), playerMana.getManaRegenRate(), playerMana.getManaEfficiency(), playerMana.addedHP, playerMana.hpRegeneration), ctx.getServerHandler().player);
            }
        }
    }
}