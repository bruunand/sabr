package com.ballthrower.communication;

import com.ballthrower.abortion.AbortCode;
import com.ballthrower.abortion.IAbortable;
import com.ballthrower.communication.packets.HandshakePacket;
import com.ballthrower.communication.packets.Packet;
import com.ballthrower.communication.packets.PacketIds;
import com.ballthrower.exceptions.UnknownPacketException;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BluetoothConnection extends Connection
{
    private NXTConnection _socket;
    private DataInputStream _inputStream;
    private DataOutputStream _outputStream;

    private final IAbortable _abortable;

    private boolean _isConnected = false;

    public BluetoothConnection(IAbortable abortable)
    {
        this._abortable = abortable;
    }

    @Override
    public void awaitConnection()
    {
        // Wait for connection and set mode to raw
        _socket = Bluetooth.waitForConnection();
        _socket.setIOMode(NXTConnection.RAW);

        // Get streams
        _inputStream = _socket.openDataInputStream();
        _outputStream = _socket.openDataOutputStream();

        // Send handshake
        HandshakePacket handshake = new HandshakePacket();
        sendPacket(handshake);

        // Receive handshake and validate token
        if (!handshake.isValidReply(receivePacket()))
            _abortable.abort(AbortCode.INVALID_HANDSHAKE);
        else
            _isConnected = true;
    }

    @Override
    public DataInputStream getInputStream()
    {
        return this._inputStream;
    }

    public DataOutputStream getOutputStream()
    {
        return this._outputStream;
    }

    @Override
    public boolean isConnected()
    {
        return this._isConnected;
    }

    @Override
    public void closeConnection()
    {
        _socket.close();
    }

    @Override
    public Packet receivePacket()
    {
        try
        {
            // The first element of each packet is the id of the packet type
            byte packetId = _inputStream.readByte();

            // Query the packet handler for the packet class associated with this id
            // The packet handler also constructs the packet object
            Packet instantiatedPacket = Packet.instantiateFromId(PacketIds.fromByte(packetId));

            // Finally we deserialize the object
            instantiatedPacket.constructFromConnection(this);

            return instantiatedPacket;
        }
        catch (IOException exception)
        {
            this._isConnected = false;

            _abortable.abort(AbortCode.GENERIC, "I/O exception.");
        }
        catch (UnknownPacketException e)
        {
            this._isConnected = false;

            _abortable.abort(AbortCode.UNKNOWN_PACKET);
        }

        return null;
    }

    @Override
    public void sendPacket(Packet packet)
    {
        try
        {
            _outputStream.writeByte(packet.getId().asByte());
            packet.writeToConnection(this);
            _outputStream.flush();
        }
        catch (IOException exception)
        {
            this._isConnected = false;

            _abortable.abort(AbortCode.GENERIC, "I/O exception.");
        }
    }
}
