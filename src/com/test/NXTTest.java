package com.test;

import com.ballthrower.exceptions.AssertException;
import com.ballthrower.targeting.TargetBox;
import com.ballthrower.targeting.TargetBoxInfo;
import com.test.communication.BluetoothConnectionTest;
import com.test.targeting.DirectionCalculatorTest;
import com.test.targeting.DistanceCalculatorTest;
import lejos.nxt.*;

import java.io.IOException;

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
        BluetoothConnectionTest blueTooth = new BluetoothConnectionTest();

        try
        {
            disCalc.runAllTests();
            dirCalc.runAllTests();
            blueTooth.runAllTests();
        }
        catch (AssertException e)
        {
            numErrors++;
            LCD.drawString("Failure from method: " + e.methodName, 0, 0);
            LCD.drawString("Cause: "               + e.message,    0, 1);
            LCD.drawString("Failed tests: "        + numErrors,    0, 2);
        }
        catch (IOException e)
        {
            /* Nothing we can do anything about here. */
        }
    }

    public static TargetBoxInfo getTestTargetBox() {
        TargetBoxInfo toReturn = new TargetBoxInfo((byte)6);

        toReturn.getTargets()[0] = new TargetBox(60F, 44F, (short)278);
        toReturn.getTargets()[1] = new TargetBox(60F, 42F, (short)279);
        toReturn.getTargets()[2] = new TargetBox(59F, 41F, (short)280);
        toReturn.getTargets()[3] = new TargetBox(59F, 40F, (short)280);
        toReturn.getTargets()[4] = new TargetBox(62F, 42F, (short)279);
        toReturn.getTargets()[5] = new TargetBox(49F, 33F, (short)286);

        return toReturn;
    }
}