package com.ballthrower;

import com.ballthrower.communication.ConnectionFactory;
import com.ballthrower.tools.CSVReader;
import com.test.NXTTest;

public class Main
{
	public static void main(String[] args)
	{
		/*
	    Robot robot = Robot.getInstance();
	    robot.addButtonListeners();
	    robot.awaitConnection(new ConnectionFactory());

	    while(true);
	    */

		NXTTest test = new NXTTest();
		test.kek();
    }
}