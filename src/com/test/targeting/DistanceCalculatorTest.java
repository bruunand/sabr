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

    public void calculateDistance() throws AssertException
    {

    }

    public void zeroSampleCountTest() throws AssertException
    {
        NXTAssert test = new NXTAssert();
        TargetBoxInfo zeroSample = new TargetBoxInfo((byte)0);

        test.assertThat(dc.calculateDistance(zeroSample), "DistanceCalculator:zeroSampleCountTest")
                .isEqualTo(Float.POSITIVE_INFINITY);

    }

    public void runAllTests() throws AssertException
    {
        setUp();
        zeroSampleCountTest();

    }
}
