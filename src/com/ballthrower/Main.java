package com.ballthrower;

import com.ballthrower.communication.ConnectionFactory;
import com.ballthrower.tools.CSVReader;

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
		System.out.println();
		String path = System.getProperty("user.dir") + "\\data\\test_data_distance.csv";
        CSVReader cr = new CSVReader();
        System.out.println(cr.getData(path).get(0));
    }
}