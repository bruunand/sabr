package com.test;

import com.sabr.exceptions.AssertException;
import com.sabr.targeting.TargetBox;
import com.sabr.targeting.TargetContainer;
import com.test.communication.BluetoothConnectionTest;
import com.test.communication.TargetInfoRequestPacketTest;
import com.test.movement.shooting.ShooterTest;
import com.test.targeting.DirectionCalculatorTest;
import com.test.targeting.DistanceCalculatorTest;
import com.test.targeting.policy.*;
import lejos.nxt.*;

/**
 * Assertions used for unit testing on the NXT
 */
public class NXTTest
{
    public void runAllTests()
    {
        int numErrors = 0;

        Test[] testSuites = new Test[]
        {
                new DirectionCalculatorTest(),
                new DistanceCalculatorTest(),
                new ShooterTest(),
                new SidePolicyTest(),
                new BiggestClusterPolicyTest(),
                new LeastRotationPolicyTest(),
                new RandomPolicyTest(),
                new PolicyFactoryTest()
        };


        for (Test testSuite : testSuites)
        {
            try
            {
                testSuite.runAllTests();
            }
            catch (AssertException e)
            {
                LCD.drawString("Tests failed: " + numErrors, 0, 0);
                LCD.drawString(e.methodName, 0, 1);
                LCD.drawString(e.message, 0, 2);
            }
        }

        if (numErrors == 0)
            LCD.drawString("All tests passed!", 0, 0);
    }

    public static TargetContainer getTestTargetBox()
    {
        TargetContainer toReturn = new TargetContainer((byte) 1);

        /* For distance and direction */
        toReturn.setTarget((byte)0, new TargetBox((short) 60, (short) 44, (short) 278));

        /* For policy targeting, height is irrelevant */
        toReturn.setTarget((byte)0, new TargetBox((short) 55, (short) 44, (short) 250));
        toReturn.setTarget((byte)0, new TargetBox((short) 50, (short) 44, (short) 240));
        toReturn.setTarget((byte)0, new TargetBox((short) 20, (short) 44, (short) 230));
        toReturn.setTarget((byte)0, new TargetBox((short) 10, (short) 44, (short) 100));

        toReturn.setFrameWidth((short)800); /* Not the actual frame width! */

        return toReturn;
    }
}