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
        /* Calculated beforehand */
        float median = 60;
        float realDistance = _focalLengthHeight * _targetHeight / median;

        test.assertThat(dc.calculateDistance(tbi), "DistanceCalculator:CalculateDistance")
                .isEqualTo(realDistance);
    }

    public void zeroSampleCountTest() throws AssertException
    {
        NXTAssert test = new NXTAssert();
        TargetBoxInfo zeroSample = new TargetBoxInfo((byte)0);

        test.assertThat(dc.calculateDistance(zeroSample), "DistanceCalculator:zeroSampleCountTest")
                .isEqualTo(Float.POSITIVE_INFINITY);

    }

    public void removeOutliersTest() throws AssertException
    {
        NXTAssert test = new NXTAssert();

        float[] heights = new float[tbi.getSampleCount()];
        for (int i = 0; i < tbi.getSampleCount(); i++)
        {
            heights[i] = tbi.getTargets()[i].getHeight();
        }

        /* One outlier in test target box info */
        test.assertThat(dc.removeOutliers(heights, dc.getMedian(heights)).length, "DistanceCalculator:removeOutliers")
                .isEqualTo(5);
    }

    public void getMedianTest() throws AssertException
    {
        NXTAssert test = new NXTAssert();

        float[] evenLength = new float[] {20, 30, 40, 50, 60, 70};
        float evenLengthMedian = (40 + 50) / 2;
        float[] unevenLength = new float[] {20, 30, 40, 50, 60};
        float unevenLengthMedian = 40;

        test.assertThat(dc.getMedian(evenLength), "DistanceCalculator:getMedianTest")
                .isEqualTo(evenLengthMedian);

        test.assertThat(dc.getMedian(unevenLength), "DistanceCalculator:getMedianTest")
                .isEqualTo(unevenLengthMedian);
    }

    public void runAllTests() throws AssertException
    {
        setUp();
        zeroSampleCountTest();
        calculateDistanceTest();
        removeOutliersTest();
        getMedianTest();
    }
}