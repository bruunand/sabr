package com.test;

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

        Sound.buzz();
        DistanceCalculatorTest disCalc = new DistanceCalculatorTest();
        Sound.buzz();
        DirectionCalculatorTest dirCalc = new DirectionCalculatorTest();

        try
        {
            Sound.beep();
            disCalc.runAllTests();
            Sound.buzz();
            dirCalc.runAllTests();
            Sound.beep();
        }
        catch (AssertException e)
        {
            Sound.beepSequence();
            System.out.println("\n -- TEST FAILED -- \nFrom method: " + e.methodName + ": "+  e.message + "\n");
            numErrors++;
        }

        return numErrors;
    }
}