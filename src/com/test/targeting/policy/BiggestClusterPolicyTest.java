package com.test.targeting.policy;

import com.sabr.exceptions.AssertException;
import com.sabr.targeting.TargetBox;
import com.sabr.targeting.TargetContainer;
import com.sabr.targeting.policies.BiggestClusterPolicy;
import com.test.NXTAssert;
import com.test.NXTTest;
import com.test.Test;

public class BiggestClusterPolicyTest extends Test
{
    private TargetContainer testContainer;
    private BiggestClusterPolicy policy;
    private TargetBox[] biggestCluster;

    private void setUp()
    {
        policy = new BiggestClusterPolicy();
        testContainer = NXTTest.getTestTargetBox();
        biggestCluster = new TargetBox[]
        {
                testContainer.getTarget((byte)0),
                testContainer.getTarget((byte)1),
                testContainer.getTarget((byte)2)
        };
    }

    private void singleTargetTest() throws AssertException
    {
        TargetContainer singleTarget = new TargetContainer((byte)1);
        singleTarget.setTarget((byte)0, new TargetBox((short)50, (short)50, (short)50));

        NXTAssert test = new NXTAssert();
        test.assertThat(policy.selectTargetBox(singleTarget), "BiggestCluster:SingleTarget")
                .isEqualTo(singleTarget.getTarget((byte)0));
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
        singleTargetTest();
        biggestClusterTest();
    }
}
