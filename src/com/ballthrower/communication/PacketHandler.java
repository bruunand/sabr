package com.ballthrower.communication;

import com.ballthrower.communication.packets.Packet;

import java.util.Dictionary;
import java.util.Hashtable;

public class PacketHandler
{
    private static Dictionary<Byte, Packet> _registeredPackets = new Hashtable<>();

    static
    {
        registerPacket(null);
    }

    private static void registerPacket(Packet packet)
    {
        _registeredPackets.put(packet.getId(), packet);
    }

    public static void handlePacket(Packet packet)
    {

    }
}
