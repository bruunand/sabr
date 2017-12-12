package com.ballthrower;

import com.ballthrower.communication.ConnectionFactory;
import com.ballthrower.targeting.policies.PolicyFactory;

public class Main
{
	public static void main(String[] args)
	{
	    Robot robot = Robot.getInstance();
	    robot.addButtonListeners();
	    robot.setTargetingPolicyType(PolicyFactory.TargetingPolicyType.Nearest);
	    robot.setConnectionType(ConnectionFactory.ConnectionType.Bluetooth);
	    robot.awaitConnection(new ConnectionFactory());

	    while(true);
    }

}