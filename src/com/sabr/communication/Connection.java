package com.sabr.communication;

import com.sabr.communication.packets.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public abstract class Connection
{
    public abstract void awaitConnection();
    public abstract void closeConnection();

    public abstract Packet receivePacket();
    public abstract void sendPacket(Packet packet);

    public abstract DataInputStream getInputStream();
    public abstract DataOutputStream getOutputStream();

    public abstract boolean isConnected();
}
