package com.test.targeting;

import com.ballthrower.targeting.DistanceCalculator;
import com.ballthrower.targeting.TargetBoxInfo;
import com.ballthrower.exceptions.AssertException;
import com.test.NXTAssert;
import com.test.NXTTest;


public class DistanceCalculatorTest
{
    private DistanceCalculator dc;
    private TargetBoxInfo tbi;

    private void setUp()
    {
        dc = new DistanceCalculator();
        tbi = NXTTest.getTestTargetBox();
    }

    public DistanceCalculatorTest()
    {

    }

    public void calculateDistance() throws AssertException
    {

    }

    public void runAllTests() throws AssertException
    {
        setUp();
        calculateDistance();
    }
}
