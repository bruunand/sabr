package Tools.unitTests.com.ballthrower.targeting;

import com.ballthrower.targeting.DistanceCalculator;
import com.ballthrower.tools.CSVReader;
import com.ballthrower.tools.TestTargetBoxInfo;
import lejos.util.Assertion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Anders Brams on 11/14/2017.
 */
class DistanceCalculatorTest {

    private ArrayList<TestTargetBoxInfo> tbis;
    private DistanceCalculator dc;

    @BeforeEach
    void setUp() {
        CSVReader cr = new CSVReader();
        tbis = cr.getData(System.getProperty("user.dir") + "\\data\\test_data_distance.csv");
        dc = new DistanceCalculator();
    }

    @AfterEach
    void tearDown() {
        tbis = null;
        dc = null;
    }

    @Test
    void calculateDistance() {
        for (TestTargetBoxInfo tbi : tbis)
        {
            float dist = dc.calculateDistance(tbi.getTargetBoxInfo());
            Assertions.assertTrue(isInRange(dist, tbi.getRealDistance(), 15));
        }
    }

    private boolean isInRange(float actual, float target, float range)
    {
        return (actual > target - range &&
                actual < target + range);
    }
}