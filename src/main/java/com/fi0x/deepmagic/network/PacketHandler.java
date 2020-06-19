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

        INSTANCE.registerMessage(PacketGetManaAmount.Handler.class, PacketGetManaAmount.class, nextID(), Side.SERVER);
        INSTANCE.registerMessage(PacketReturnManaAmount.Handler.class, PacketReturnManaAmount.class, nextID(), Side.CLIENT);
    }
}