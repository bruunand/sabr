package com.test.targeting.policy;

import com.ballthrower.exceptions.AssertException;
import com.ballthrower.targeting.TargetBox;
import com.ballthrower.targeting.TargetContainer;
import com.ballthrower.targeting.policies.BiggestClusterPolicy;
import com.test.NXTAssert;
import com.test.NXTTest;
import com.test.Test;

/**
 * Created by Anders Brams on 12/12/2017.
 */
public class BiggestClusterPolicyTest extends Test {
    TargetContainer testContainer;
    BiggestClusterPolicy policy;
    TargetBox[] biggestCluster;

    private void setUp()
    {
        policy = new BiggestClusterPolicy();
        testContainer = NXTTest.getTestTargetBox();
        biggestCluster = new TargetBox[] {
                testContainer.getTarget((byte)0),
                testContainer.getTarget((byte)1),
                testContainer.getTarget((byte)2)
        };
    }

    private void biggestClusterTest() throws AssertException
    {
        TargetBox selectedTarget = policy.selectTargetBox(testContainer);
        NXTAssert test = new NXTAssert();

        test.assertThat(selectedTarget, "BiggestCluster:selectTarget")
                .isIn(biggestCluster);
    }

    @Override
    public void runAllTests() throws AssertException
    {
        setUp();
        biggestClusterTest();
    }
}
