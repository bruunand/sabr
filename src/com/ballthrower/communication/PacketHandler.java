package com.ballthrower.communication;

import com.ballthrower.communication.packets.HandshakePacket;
import com.ballthrower.communication.packets.Packet;
import com.ballthrower.exceptions.UnknownPacketException;

/* Due to limitations of the leJOS VM, it is not possible to use Java's reflection to instantiate packets */
public class PacketHandler
{
    public static Packet instantiateFromId(PacketIds id) throws UnknownPacketException
    {
        switch (id)
        {
            case Handshake:
                return new HandshakePacket();
            default:
                throw new UnknownPacketException("Packet Id " + id + " is unknown.");
        }
    }

    public enum PacketIds
    {
        Handshake((byte) 0x00);

        private byte _id;

        PacketIds(byte id)
        {
            _id = id;
        }

        public byte asByte()
        {
            return this._id;
        }
        
        public static PacketIds fromByte(byte value)
        {
            return PacketIds.values()[value];
        }
    }
}
