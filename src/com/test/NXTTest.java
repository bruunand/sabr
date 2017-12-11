package com.test;

import com.ballthrower.exceptions.AssertException;
import com.ballthrower.targeting.TargetBox;
import com.ballthrower.targeting.TargetContainer;
import lejos.nxt.*;

/**
 * Assertions used for unit testing on the NXT
 */
public class NXTTest
{
    /** Runs all tests and returns the number of failed tests. */
    public void runAllTests()
    {
        int numErrors = 0;

        /*Test disCalc = new DistanceCalculatorTest();
        Test dirCalc = new DirectionCalculatorTest();
        Test shooter = new ShooterTest();
        Test requestPacket = new TargetInfoRequestPacketTest();*/

        try
        {
            /*disCalc.runAllTests();
            dirCalc.runAllTests();
            shooter.runAllTests();
            requestPacket.runAllTests();*/
        }
        catch (AssertException e)
        {
            numErrors++;
            LCD.drawString("Tests failed: " + numErrors, 0, 0);
            LCD.drawString(e.methodName, 0, 1);
            LCD.drawString(e.message, 0, 2);
        }

        if (numErrors == 0)
            LCD.drawString("All tests passed!", 0, 0);
    }

    public static TargetContainer getTestTargetBox()
    {
        TargetContainer toReturn = new TargetContainer((byte) 6);

        toReturn.setTarget((byte) 0, new TargetBox((short) 60, (short) 44, (short) 278));
        toReturn.setTarget((byte) 1, new TargetBox((short) 60, (short) 42, (short) 279));
        toReturn.setTarget((byte) 2, new TargetBox((short) 59, (short) 41, (short) 280));
        toReturn.setTarget((byte) 3, new TargetBox((short) 59, (short) 40, (short) 280));
        toReturn.setTarget((byte) 4, new TargetBox((short) 62, (short) 42, (short) 279));
        toReturn.setTarget((byte) 5, new TargetBox((short) 49, (short) 33, (short) 286));

        return toReturn;
    }
}