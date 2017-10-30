package com.ballthrower.communication;

import com.ballthrower.communication.packets.HandshakePacket;
import com.ballthrower.communication.packets.Packet;
import com.ballthrower.communication.packets.PacketIds;
import com.ballthrower.exceptions.UnknownPacketException;
import lejos.nxt.LCD;
import lejos.nxt.Sound;
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
        sendPacket(new HandshakePacket());

        // Receive handshake and validate token
        if (handshake.isValidReply(receivePacket()))
        {
            LCD.clear();
            LCD.drawString("Connected", 0, 0);
            Sound.beep();
        }
        // Todo: Error handling if not handshake
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
            LCD.drawString("IO Exception.", 0, 3);
        }
        catch (UnknownPacketException e)
        {
            LCD.drawString("Unknown packet type.", 0, 3);
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
            LCD.drawString("IO Exception.", 0, 3);
        }
	}
}