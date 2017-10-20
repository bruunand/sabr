package com.ballthrower.communication;

import com.ballthrower.communication.packets.EnginePowerPacket;
import com.ballthrower.communication.packets.Packet;
import com.ballthrower.communication.packets.PingPacket;
import com.ballthrower.exceptions.UnknownPacketException;

/* Due to limitations of the leJOS VM, it is not possible to use Java's reflection to instantiate packets */
public class PacketHandler
{
    public static Packet instantiateFromId(PacketIds id) throws UnknownPacketException
    {
        switch (id)
        {
            case EnginePower:
                return new EnginePowerPacket();
            case Ping:
                return new PingPacket();
            default:
                throw new UnknownPacketException("Packet Id " + id + " is unknown.");
        }
    }

    public enum PacketIds
    {
        EnginePower((byte) 0x00),
        Ping((byte) 0x01);

        private byte _id;

        PacketIds(byte id)
        {
            _id = id;
        }

        public static PacketIds fromByte(byte value)
        {
            return PacketIds.values()[value];
        }
    }
}
