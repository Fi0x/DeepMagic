package com.fi0x.deepmagic.network;

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
    }
}