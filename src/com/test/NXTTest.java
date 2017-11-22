package com.test;

import com.test.targeting.DirectionCalculatorTest;
import com.test.targeting.DistanceCalculatorTest;

/**
 * Assertions used for unit testing on the NXT
 */
public class NXTTest
{

    /** Runs all tests and returns the number of failed tests. */
    public int runAllTests() {
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

        return numErrors;
    }
}