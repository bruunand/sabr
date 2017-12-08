package com.ballthrower;
import com.ballthrower.communication.ConnectionFactory;
import com.ballthrower.targeting.policies.PolicyFactory;

public class Main
{
	public static void main(String[] args)
	{
	    Robot robot = Robot.getInstance();
	    robot.addButtonListeners();
	    robot.awaitConnection(new ConnectionFactory());

	    while(true);

		//runTests();
		//while (true){}
    }

    /*public static void runTests()
	{
		NXTTest tests = new NXTTest();
		tests.runAllTests();
	}*/
}