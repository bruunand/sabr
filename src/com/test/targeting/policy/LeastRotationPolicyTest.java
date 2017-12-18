package com.test.targeting.policy;

import com.ballthrower.exceptions.AssertException;
import com.ballthrower.targeting.TargetBox;
import com.ballthrower.targeting.TargetContainer;
import com.ballthrower.targeting.policies.LeastRotationPolicy;
import com.test.NXTAssert;
import com.test.NXTTest;
import com.test.Test;

public class LeastRotationPolicyTest extends Test
{
    TargetContainer testContainer;
    LeastRotationPolicy policy;

    private void setUp()
    {
        testContainer = NXTTest.getTestTargetBox();
        policy = new LeastRotationPolicy();
    }

    private void zeroSampleTest() throws AssertException
    {
        TargetContainer zeroSample = new TargetContainer((byte)0);

        NXTAssert test = new NXTAssert();
        test.assertThat(policy.selectTargetBox(zeroSample), "LeastRotationPolicy:zeroSample")
                .isNull();
    }

    private void singleTargetTest() throws AssertException
    {
        TargetContainer singleTarget = new TargetContainer((byte)1);
        singleTarget.setTarget((byte)0, new TargetBox((short)50, (short)50, (short)50));

        NXTAssert test = new NXTAssert();
        test.assertThat(policy.selectTargetBox(singleTarget), "LeastRotationPolicy:singleSample")
                .isEqualTo(singleTarget.getTarget((byte)0));
    }

    private void multipleTargetsTest() throws AssertException
    {
        NXTAssert test = new NXTAssert();

        /* Frame mid is 400; closest target is the first one with x = 60 */
        test.assertThat(policy.selectTargetBox(testContainer), "LeastRotationPolicy:multipleTargets")
                .isEqualTo(testContainer.getTarget((byte)0));
    }

    @Override
    public void runAllTests() throws AssertException
    {
        zeroSampleTest();
        singleTargetTest();
        multipleTargetsTest();
        setUp();
    }
}
