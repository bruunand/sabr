package com.ballthrower.communication;

import com.ballthrower.communication.packets.EnginePowerPacket;
import com.ballthrower.communication.packets.Packet;
import com.ballthrower.communication.packets.RequestTargetPacket;
import com.ballthrower.communication.packets.RotateRequestPacket;
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
            case RequestTarget:
                return new RequestTargetPacket();
            case RotateRequest:
                return new RotateRequestPacket();
            default:
                throw new UnknownPacketException("Packet Id " + id + " is unknown.");
        }
    }

    public enum PacketIds
    {
        EnginePower((byte) 0x00),
        RequestTarget((byte) 0x01),
        RotateRequest((byte) 0x02);

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
