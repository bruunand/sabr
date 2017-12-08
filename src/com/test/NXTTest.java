package com.test;

import com.ballthrower.exceptions.AssertException;
import com.ballthrower.targeting.TargetBox;
import com.ballthrower.targeting.TargetContainer;
import com.test.communication.TargetInfoRequestPacketTest;
import com.test.movement.shooting.ShooterTest;
import com.test.targeting.DirectionCalculatorTest;
import com.test.targeting.DistanceCalculatorTest;
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

        DistanceCalculatorTest disCalc = new DistanceCalculatorTest();
        DirectionCalculatorTest dirCalc = new DirectionCalculatorTest();
        ShooterTest shooter = new ShooterTest();
        TargetInfoRequestPacketTest requestPacket = new TargetInfoRequestPacketTest();

        try
        {
            disCalc.runAllTests();
            dirCalc.runAllTests();
            shooter.runAllTests();
            requestPacket.runAllTests();
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

    public static TargetContainer getTestTargetBox() {
        TargetContainer toReturn = new TargetContainer((byte)6);

        toReturn.getTargets()[0] = new TargetBox(60F, 44F, (short)278);
        toReturn.getTargets()[1] = new TargetBox(60F, 42F, (short)279);
        toReturn.getTargets()[2] = new TargetBox(59F, 41F, (short)280);
        toReturn.getTargets()[3] = new TargetBox(59F, 40F, (short)280);
        toReturn.getTargets()[4] = new TargetBox(62F, 42F, (short)279);
        toReturn.getTargets()[5] = new TargetBox(49F, 33F, (short)286);

        return toReturn;
    }
}