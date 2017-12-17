package com.ballthrower;

import com.ballthrower.communication.ConnectionFactory;
import com.ballthrower.targeting.policies.PolicyFactory;
import com.tools.PowerTest;

public class Main
{
	public static void main(String[] args)
	{
	    Robot robot = Robot.getInstance();
	    robot.addButtonListeners();
	    robot.setTargetingPolicyType(PolicyFactory.TargetingPolicyType.Random);
	    robot.setConnectionType(ConnectionFactory.ConnectionType.Bluetooth);
        robot.setDebug(true);
	    robot.awaitConnection(new ConnectionFactory());

	    while(true);
        //powerTest();
    }

    public static void powerTest()
	{
		PowerTest test = new PowerTest();
		test.main(new String[] {});
	}

}
