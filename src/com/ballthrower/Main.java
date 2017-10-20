package com.ballthrower;

import com.ballthrower.communication.BluetoothCommunicator;
import com.ballthrower.communication.Communicator;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Sound;

public class Main
{
	public static void main(String[] args)
	{
        Sound.buzz();
        LCD.drawString("waiting", 0, 0);
        Communicator communicator = new BluetoothCommunicator();
		communicator.awaitConnection();
        Button.waitForAnyPress();
    }
}
