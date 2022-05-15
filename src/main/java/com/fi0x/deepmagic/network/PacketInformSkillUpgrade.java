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

public class PacketInformSkillUpgrade implements IMessage
{
    private boolean messageValid;
    private String playerName;
    private int skillID;

    public PacketInformSkillUpgrade()
    {
        messageValid = false;
    }
    public PacketInformSkillUpgrade(String playerName, int upgradedSkillID)
    {
        this.playerName = playerName;
        this.skillID = upgradedSkillID;

        messageValid = true;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        try
        {
            playerName = ByteBufUtils.readUTF8String(buf);
            skillID = buf.readInt();
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
        buf.writeInt(skillID);
    }

    public static class Handler implements IMessageHandler<PacketInformSkillUpgrade, IMessage>
    {
        @Override
        public IMessage onMessage(PacketInformSkillUpgrade message, MessageContext ctx)
        {
            if(!message.messageValid && ctx.side != Side.SERVER) return null;
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));
            return null;
        }


        void processMessage(PacketInformSkillUpgrade message, MessageContext ctx)
        {
            EntityPlayer player = ctx.getServerHandler().player.getServerWorld().getPlayerEntityByName(message.playerName);
            if(player != null && player.hasCapability(PlayerProperties.PLAYER_MANA, null))
            {
                PlayerMana playerMana = player.getCapability(PlayerProperties.PLAYER_MANA, null);
                assert playerMana != null;

                playerMana.removeSkillpoint();
                switch(message.skillID)
                {
                    case 1:
                        playerMana.maxManaMultiplier++;
                        break;
                    case 2:
                        playerMana.setManaRegenRate(playerMana.getManaRegenRate() + 1);
                        break;
                    case 3:
                        playerMana.setManaEfficiencyValue(playerMana.getManaEfficiencyValue() + 1);
                        break;
                    case 4:
                        playerMana.addedHP++;
                        break;
                    case 5:
                        playerMana.hpRegeneration++;
                        break;
                    case 6:
                        playerMana.addSpellTier();
                        break;
                }
                PacketHandler.INSTANCE.sendTo(new PacketReturnSkill(playerMana.maxManaMultiplier, playerMana.getSkillXP(), playerMana.getSkillpoints(), playerMana.getManaRegenRate(), playerMana.getManaEfficiencyValue(), playerMana.addedHP, playerMana.hpRegeneration, playerMana.getSpellTier()), ctx.getServerHandler().player);
            }
        }
    }
}