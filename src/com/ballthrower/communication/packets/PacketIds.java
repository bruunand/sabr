package com.ballthrower.communication.packets;

public enum PacketIds
{
    Handshake((byte) 0x0),
    TargetDirectionRequest((byte) 0x1),
    Debug((byte) 0x2);

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