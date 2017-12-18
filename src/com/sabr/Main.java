package com.sabr;

import com.sabr.communication.ConnectionFactory;
import com.sabr.targeting.policies.PolicyFactory;
import com.tools.PowerTest;
import com.test.*;

public class Main
{
	public static void main(String[] args)
	{
        NXTTest test = new NXTTest();
        test.runAllTests();
        
	    Robot robot = Robot.getInstance();
	    robot.addButtonListeners();
	    robot.setTargetingPolicyType(PolicyFactory.TargetingPolicyType.Random);
	    robot.setConnectionType(ConnectionFactory.ConnectionType.Bluetooth);
        robot.setDebug(true);
	    robot.awaitConnection(new ConnectionFactory());
        //powerTest();
    }

    public static void powerTest()
	{
		PowerTest test = new PowerTest();
		test.main(new String[] {});
	}

}
