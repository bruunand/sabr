package com.ballthrower;

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

		runTests();

    }

    public static void runTests()
	{
		NXTTest tests = new NXTTest();
		tests.runAllTests();
	}
}