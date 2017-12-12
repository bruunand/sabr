package com.test.targeting;

import com.ballthrower.targeting.DistanceCalculator;
import com.ballthrower.targeting.TargetContainer;
import com.ballthrower.exceptions.AssertException;
import com.test.NXTAssert;
import com.test.NXTTest;
import com.test.Test;

public class DistanceCalculatorTest extends Test
{
    private DistanceCalculator dc;
    private TargetContainer tbi;

    // physical height manually measured
    private static final float _targetHeight = 10.2f;
    // height represented on the image plane in pixels
    private static final float _knownHeight = 52.0f;
    // physical distance measured manually
    private static final float _knownRealDistance = 100.0f;
    // focallength is the distance between the image plane and lens of the camera
    private static final float _focalLengthHeight = _knownHeight * _knownRealDistance / _targetHeight;

    private void setUp()
    {
        dc = new DistanceCalculator();
        tbi = NXTTest.getTestTargetBox();
    }

    public void calculateDistanceTest() throws AssertException
    {
        NXTAssert test = new NXTAssert();
        float realDistance = _focalLengthHeight * _targetHeight / 278;

        test.assertThat(dc.calculateDistance(tbi.getTarget((byte)0)), "DistanceCalculator:CalculateDistance")
                .isEqualTo(realDistance);
    }

    @Override
    public void runAllTests() throws AssertException
    {
        setUp();
        calculateDistanceTest();
    }
}
