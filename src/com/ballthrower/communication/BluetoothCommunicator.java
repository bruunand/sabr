package com.ballthrower.communication;

import lejos.nxt.*;
import lejos.nxt.comm.*;

import java.io.DataInputStream;
import java.io.IOException;

public class BluetoothCommunicator extends Communicator
{
    private NXTMotor motor = new NXTMotor(MotorPort.A);
    private NXTConnection _socket;
	private DataInputStream _inputStream;

	@Override
	public void awaitConnection()
	{
		_socket = Bluetooth.waitForConnection();
		_socket.setIOMode(NXTConnection.RAW);
		motor.forward();
		Sound.beepSequence();
		receivePacket();
	}

    @Override
    public void closeConnection()
    {
        _socket.close();
    }

    @Override
    public boolean isAlive()
    {
        return false;
    }

    @Override
	public Packet receivePacket()
	{
	    try
        {
            byte packetHeader = _inputStream.readByte();
        }
        catch (IOException exception)
        {
            LCD.drawString("IO Exception", 0, 3);
        }

        return null;
	}

	@Override
	public void sendPacket(Packet packet)
	{

	}
}