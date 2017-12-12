package com.test.targeting;

import com.ballthrower.targeting.DirectionCalculator;
import com.ballthrower.targeting.ITargetContainer;
import com.ballthrower.targeting.TargetContainer;
import com.ballthrower.exceptions.AssertException;
import com.test.NXTAssert;
import com.test.NXTTest;

public class DirectionCalculatorTest
{
    private DirectionCalculator dc;
    private ITargetContainer tbi;

    private void setUp()
    {
        dc = new DirectionCalculator();
        tbi = NXTTest.getTestTargetBox();
    }

    private void sampleCountZeroTest() throws AssertException
    {
        TargetContainer zeroSample = new TargetContainer((byte)0);

        NXTAssert test = new NXTAssert();

        test.assertThat(dc.calculateMeanPixelDistance(zeroSample), "DirectionCalculator:sampleCountZeroTest")
                .isEqualTo(Float.POSITIVE_INFINITY);
    }

    private void meanPixelDistanceTest() throws AssertException
    {
        /* Calculated beforehand */
        float actualMeanPixelDistance = 499.5f;

        tbi.setFrameWidth((short)1600);

        NXTAssert test = new NXTAssert();

        test.assertThat(dc.calculateMeanPixelDistance(tbi), "DirectionCalculator:meanPixelDistanceTest")
                .isEqualTo(actualMeanPixelDistance);
    }

    public void runAllTests() throws AssertException
    {
        setUp();
        sampleCountZeroTest();
        meanPixelDistanceTest();
    }
}

