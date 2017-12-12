package com.test.targeting.policy;

import com.ballthrower.exceptions.AssertException;
import com.ballthrower.targeting.TargetBox;
import com.ballthrower.targeting.TargetContainer;
import com.ballthrower.targeting.policies.RandomPolicy;
import com.test.NXTAssert;
import com.test.NXTTest;
import com.test.Test;

/**
 * Created by Anders Brams on 12/12/2017.
 */
public class RandomPolicyTest extends Test {
    TargetContainer testContainer;
    RandomPolicy policy;

    private void setUp()
    {
        testContainer = NXTTest.getTestTargetBox();
        policy = new RandomPolicy();
    }

    private void zeroSampleTest() throws AssertException
    {
        TargetContainer zeroSamples = new TargetContainer((byte)0);

        NXTAssert test = new NXTAssert();
        test.assertThat(policy.selectTargetBox(zeroSamples), "RandomPolicy:zeroSamples")
                .isNull();
    }

    private void singleTargetTest() throws AssertException
    {
        TargetContainer singleTarget = new TargetContainer((byte)1);
        singleTarget.setTarget((byte)0, new TargetBox((short)50, (short)50, (short)50));

        NXTAssert test = new NXTAssert();
        test.assertThat(policy.selectTargetBox(singleTarget), "RandomPolicy:singleTarget")
                .isEqualTo(singleTarget.getTarget((byte)0));
    }

    private void multipleTargetsTest() throws AssertException
    {
        NXTAssert test = new NXTAssert();
        test.assertThat(policy.selectTargetBox(testContainer), "RandomPolicy:singleTarget")
                .isIn(testContainer.cloneTargets());
    }

    @Override
    public void runAllTests() throws AssertException {
        setUp();
        zeroSampleTest();
        singleTargetTest();
        multipleTargetsTest();
    }
}
