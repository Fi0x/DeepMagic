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

public class PacketGetSkill implements IMessage
{
    private boolean messageValid;
    private String playerName;

    private double skillXP;
    private int freeSkillpoints;
    private double manaRegenRate;
    private double manaEfficiency;
    private int manaMultiplier;
    private int addedHP;
    private int hpRegeneration;

    public PacketGetSkill()
    {
        messageValid = false;
    }
    public PacketGetSkill(String playerName, double skillXP, int freeSkillpoints, double manaRegenRate, double manaEfficiency, int manaMultiplier, int addedHP, int hpRegeneration)
    {
        this.playerName = playerName;
        this.skillXP = skillXP;
        this.freeSkillpoints = freeSkillpoints;
        this.manaRegenRate = manaRegenRate;
        this.manaEfficiency = manaEfficiency;
        this.manaMultiplier = manaMultiplier;
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
            skillXP = buf.readDouble();
            freeSkillpoints = buf.readInt();
            manaRegenRate = buf.readDouble();
            manaEfficiency = buf.readDouble();
            manaMultiplier = buf.readInt();
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
        buf.writeDouble(skillXP);
        buf.writeInt(freeSkillpoints);
        buf.writeDouble(manaRegenRate);
        buf.writeDouble(manaEfficiency);
        buf.writeInt(manaMultiplier);
        buf.writeInt(addedHP);
        buf.writeInt(hpRegeneration);
    }

    public static class Handler implements IMessageHandler<PacketGetSkill, IMessage>
    {

        @Override
        public IMessage onMessage(PacketGetSkill message, MessageContext ctx)
        {
            if(!message.messageValid && ctx.side != Side.SERVER) return null;
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));
            return null;
        }

        void processMessage(PacketGetSkill message, MessageContext ctx)
        {
            System.out.println("Get-Message processing");
            EntityPlayer player = ctx.getServerHandler().player.getServerWorld().getPlayerEntityByName(message.playerName);
            if(player != null && player.hasCapability(PlayerProperties.PLAYER_MANA, null))
            {
                PlayerMana playerMana = player.getCapability(PlayerProperties.PLAYER_MANA, null);
                assert playerMana != null;
                PacketHandler.INSTANCE.sendTo(new PacketReturnSkill(message.playerName, playerMana.getSkillXP(), playerMana.getSkillpoints(), playerMana.getManaRegenRate(), playerMana.getManaEfficiency(), playerMana.maxManaMultiplier, playerMana.addedHP, playerMana.hpRegeneration), ctx.getServerHandler().player);
            }
        }
    }
}