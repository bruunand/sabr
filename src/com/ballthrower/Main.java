package com.ballthrower;

import com.ballthrower.communication.BluetoothCommunicator;
import com.ballthrower.communication.Communicator;
import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Main
{
	public static void main(String[] args)
	{
        LCD.drawString("Awaiting connection", 0, 0);
        Communicator communicator = new BluetoothCommunicator();
		communicator.awaitConnection();

		while (true)
        {
            Button.ENTER.waitForPressAndRelease();
        }
    }
}