package com.test.targeting;

import com.ballthrower.targeting.DirectionCalculator;
import com.ballthrower.tools.CSVReader;
import com.ballthrower.tools.TestTargetBoxInfo;
import com.test.AssertException;
import com.test.NXTAssert;

import java.util.ArrayList;


public class DirectionCalculatorTest
{
    private DirectionCalculator dc;
    private ArrayList<TestTargetBoxInfo> tbis;
    private float degreesPerPixel = 0.133F;

    private void setUp()
    {
        CSVReader cr = new CSVReader();
        tbis = cr.getData("testdata.csv");
        dc = new DirectionCalculator();
    }

    public void directionPixelIntegrationTest() throws AssertException {
        for (TestTargetBoxInfo ttbi : tbis) {
            float dir = dc.calculateDirection(ttbi.getTargetBoxInfo());
            float pixelDist = dc.calculateMeanPixelDistance(ttbi.getTargetBoxInfo());

            NXTAssert test = new NXTAssert();

            test.assertThat(dir, "directionPixelIntegrationTest")
                    .isEqualTo(pixelDist * degreesPerPixel);

            if (dir < 0) {
                test.assertThat(pixelDist < 0, "directionPixelIntegrationTest")
                        .isTrue();
            }

            if (dir > 0) {
                test.assertThat(pixelDist > 0, "directionPixelIntegrationTest")
                        .isTrue();
            }
        }
    }

    public void runAllTests() throws AssertException
    {
        setUp();
        directionPixelIntegrationTest();
    }
}

