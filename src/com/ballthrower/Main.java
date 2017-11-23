package com.ballthrower;

import com.ballthrower.targeting.DirectionCalculator;
import com.ballthrower.targeting.DistanceCalculator;
import com.ballthrower.targeting.TargetBoxInfo;
import com.ballthrower.tools.CSVReader;
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
		DistanceCalculator dc = new DistanceCalculator();
		TargetBoxInfo tbi = NXTTest.getTestTargetBox();
		System.out.println(dc.calculateDistance(tbi));
	}
}