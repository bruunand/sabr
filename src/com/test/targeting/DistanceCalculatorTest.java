package com.test.targeting;

import com.ballthrower.targeting.DistanceCalculator;
import com.ballthrower.tools.CSVReader;
import com.ballthrower.tools.TestTargetBoxInfo;
import com.test.AssertException;
import com.test.NXTAssert;

import java.util.ArrayList;

public class DistanceCalculatorTest
{
    private DistanceCalculator dc;
    private ArrayList<TestTargetBoxInfo> tbis;

    public DistanceCalculatorTest()
    {
        CSVReader cr = new CSVReader();
        tbis = cr.getData("testdata.csv");
        dc = new DistanceCalculator();
    }

    public void calculateDistance() throws AssertException
    {
        float uncertainty = 15;

        for (TestTargetBoxInfo ttbi : tbis)
        {
            float dir = dc.calculateDistance(ttbi.getTargetBoxInfo());
            NXTAssert result = new NXTAssert();

            result.assertThat(dir, "calculateDirection")
                    .isInRangeOf(ttbi.getRealDistance(), uncertainty);
        }
    }

    public void runAllTests() throws AssertException
    {
        calculateDistance();
    }
}
