package com.ballthrower;

import com.test.NXTTest;
import lejos.nxt.LCD;

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

		runTests();

    }

    public static void runTests()
	{
		NXTTest test = new NXTTest();
		LCD.drawString("Failed tests: " + test.runAllTests(), 0, 0);
	}
}