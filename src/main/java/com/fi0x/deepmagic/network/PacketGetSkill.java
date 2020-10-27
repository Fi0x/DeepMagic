package com.fi0x.deepmagic.network;

import com.fi0x.deepmagic.Main;
import com.fi0x.deepmagic.mana.player.PlayerMana;
import com.fi0x.deepmagic.mana.player.PlayerProperties;
import com.fi0x.deepmagic.util.handlers.PacketHandler;
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

    private double maxMana;
    private double skillXP;
    private int skillpoints;
    private double manaRegenRate;
    private double manaEfficiency;
    private int addedHP;
    private int hpRegeneration;
    private int spellTier;
    private int spellCastSkill;

    public PacketGetSkill()
    {
        messageValid = false;
    }
    public PacketGetSkill(String playerName, double maxMana, double skillXP, int skillpoints, double manaRegenRate, double manaEfficiency, int addedHP, int hpRegeneration, int spellTier, int spellCastSkill)
    {
        this.playerName = playerName;
        this.maxMana = maxMana;
        this.skillXP = skillXP;
        this.skillpoints = skillpoints;
        this.manaRegenRate = manaRegenRate;
        this.manaEfficiency = manaEfficiency;
        this.addedHP = addedHP;
        this.hpRegeneration = hpRegeneration;
        this.spellTier = spellTier;
        this.spellCastSkill = spellCastSkill;

        messageValid = true;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        try
        {
            playerName = ByteBufUtils.readUTF8String(buf);
            maxMana = buf.readDouble();
            skillXP = buf.readDouble();
            skillpoints = buf.readInt();
            manaRegenRate = buf.readDouble();
            manaEfficiency = buf.readDouble();
            addedHP = buf.readInt();
            hpRegeneration = buf.readInt();
            spellTier = buf.readInt();
            spellCastSkill = buf.readInt();
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
        buf.writeDouble(maxMana);
        buf.writeDouble(skillXP);
        buf.writeInt(skillpoints);
        buf.writeDouble(manaRegenRate);
        buf.writeDouble(manaEfficiency);
        buf.writeInt(addedHP);
        buf.writeInt(hpRegeneration);
        buf.writeInt(spellTier);
        buf.writeInt(spellCastSkill);
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
            EntityPlayer player = ctx.getServerHandler().player.getServerWorld().getPlayerEntityByName(message.playerName);
            if(player != null && player.hasCapability(PlayerProperties.PLAYER_MANA, null))
            {
                PlayerMana playerMana = player.getCapability(PlayerProperties.PLAYER_MANA, null);
                assert playerMana != null;
                PacketHandler.INSTANCE.sendTo(new PacketReturnSkill(playerMana.maxManaMultiplier, playerMana.getSkillXP(), playerMana.getSkillpoints(), playerMana.getManaRegenRate(), playerMana.getManaEfficiency(), playerMana.addedHP, playerMana.hpRegeneration, playerMana.getSpellTier(), playerMana.spellCastSkill), ctx.getServerHandler().player);
            }
        }
    }
}