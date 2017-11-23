package com.test;

import com.ballthrower.exceptions.AssertException;
import com.ballthrower.targeting.TargetBox;
import com.ballthrower.targeting.TargetBoxInfo;
import com.test.targeting.DirectionCalculatorTest;
import com.test.targeting.DistanceCalculatorTest;
import lejos.nxt.*;

/**
 * Assertions used for unit testing on the NXT
 */
public class NXTTest
{
    /** Runs all tests and returns the number of failed tests. */
    public int runAllTests()
    {
        int numErrors = 0;

        DistanceCalculatorTest disCalc = new DistanceCalculatorTest();
        DirectionCalculatorTest dirCalc = new DirectionCalculatorTest();

        try
        {
            disCalc.runAllTests();
            dirCalc.runAllTests();
        }
        catch (AssertException e)
        {
            System.out.println("\n -- TEST FAILED -- \nFrom method: " + e.methodName + ": "+  e.message + "\n");
            numErrors++;
        }

        System.out.println("All tests run. " + numErrors + " tests failed.");
        return numErrors;
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