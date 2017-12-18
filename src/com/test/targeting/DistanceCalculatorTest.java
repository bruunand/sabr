package com.test.targeting;

import com.sabr.targeting.DistanceCalculator;
import com.sabr.targeting.TargetContainer;
import com.sabr.exceptions.AssertException;
import com.test.NXTAssert;
import com.test.NXTTest;
import com.test.Test;

public class DistanceCalculatorTest extends Test
{
    private TargetContainer tbi;

    // physical height manually measured
    private static final float _targetHeight = 12.0f;
    // height represented on the image plane in pixels
    private static final float _knownHeight = 133.0f;
    // physical distance measured manually
    private static final float _knownRealDistance = 120f;
    // focallength is the distance between the image plane and lens of the camera
    private static final float _focalLengthHeight = _knownHeight * _knownRealDistance / _targetHeight;

    private void setUp()
    {
        tbi = NXTTest.getTestTargetBox();
    }

    public void calculateDistanceTest() throws AssertException
    {
        NXTAssert test = new NXTAssert();
        /* 278 = height of the first target box in target container */
        float realDistance = _focalLengthHeight * _targetHeight / 278;

        test.assertThat(DistanceCalculator.calculateDistance(tbi.getTarget((byte)0)), "DistanceCalculator:CalculateDistance")
                .isEqualToFloat(realDistance);
    }

    @Override
    public void runAllTests() throws AssertException
    {
        setUp();
        calculateDistanceTest();
    }
}
