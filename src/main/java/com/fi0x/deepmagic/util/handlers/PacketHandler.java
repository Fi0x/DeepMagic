package com.fi0x.deepmagic.util.handlers;

import com.fi0x.deepmagic.network.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler
{
    public static SimpleNetworkWrapper INSTANCE;
    private static int ID = 0;

    private static int nextID()
    {
        return ID++;
    }

    public static void registerMessages(String channelName)
    {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);

        INSTANCE.registerMessage(PacketGetPlayerMana.Handler.class, PacketGetPlayerMana.class, nextID(), Side.SERVER);
        INSTANCE.registerMessage(PacketReturnPlayerMana.Handler.class, PacketReturnPlayerMana.class, nextID(), Side.CLIENT);

        INSTANCE.registerMessage(PacketGetSkill.Handler.class, PacketGetSkill.class, nextID(), Side.SERVER);
        INSTANCE.registerMessage(PacketReturnSkill.Handler.class, PacketReturnSkill.class, nextID(), Side.CLIENT);

        INSTANCE.registerMessage(PacketGetMobAnimation.Handler.class, PacketGetMobAnimation.class, nextID(), Side.SERVER);
        INSTANCE.registerMessage(PacketReturnMobAnimation.Handler.class, PacketReturnMobAnimation.class, nextID(), Side.CLIENT);
    }
}