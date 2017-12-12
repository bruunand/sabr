package com.test.targeting.policy;

import com.ballthrower.exceptions.AssertException;
import com.ballthrower.targeting.TargetContainer;
import com.ballthrower.targeting.policies.SideFirstPolicy;
import com.test.NXTAssert;
import com.test.NXTTest;
import com.test.Test;

public class SidePolicyTest extends Test
{
    SideFirstPolicy policy;
    TargetContainer testContainer;

    private void setUp()
    {
        policy = new SideFirstPolicy(SideFirstPolicy.Side.Left);
        testContainer = NXTTest.getTestTargetBox();
    }
    private void zeroSampleTest() throws AssertException
    {
        TargetContainer zeroSample = new TargetContainer((byte)0);

        NXTAssert test = new NXTAssert();
        test.assertThat(policy.selectTargetBox(zeroSample), "SidePolicy:zeroSample")
                .isNull();
    }

    @Override
    public void runAllTests() throws AssertException {
        setUp();
        zeroSampleTest();
    }
}