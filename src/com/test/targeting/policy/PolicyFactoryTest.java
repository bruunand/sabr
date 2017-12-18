package com.test.targeting.policy;

import com.sabr.exceptions.AssertException;
import com.sabr.targeting.policies.LeastRotationPolicy;
import com.sabr.targeting.policies.PolicyFactory;
import com.sabr.targeting.policies.SideFirstPolicy;
import com.test.NXTAssert;
import com.test.Test;

public class PolicyFactoryTest extends Test
{
    private void sideFirstTest() throws AssertException
    {
        NXTAssert test = new NXTAssert();
        test.assertThat(PolicyFactory.getPolicy
                       (PolicyFactory.TargetingPolicyType.RightFirst)
                        instanceof SideFirstPolicy, "PolicyFactory:sideFirst")
                .isTrue();

        test.assertThat(PolicyFactory.getPolicy
                (PolicyFactory.TargetingPolicyType.LeftFirst)
                instanceof SideFirstPolicy, "PolicyFactory:sideFirst")
                .isTrue();
    }

    private void leastRotationTest() throws AssertException
    {
        NXTAssert test = new NXTAssert();
        test.assertThat(PolicyFactory.getPolicy
                (PolicyFactory.TargetingPolicyType.Nearest)
                instanceof LeastRotationPolicy, "PolicyFactory:leastRotation")
                .isTrue();
    }

    private void biggestClusterTest() throws AssertException
    {
        NXTAssert test = new NXTAssert();
        test.assertThat(PolicyFactory.getPolicy
                (PolicyFactory.TargetingPolicyType.BiggestCluster)
                instanceof DoublePolicy, "PolicyFactory:biggestCluster")
                .isTrue();
    }

    @Override
    public void runAllTests() throws AssertException
    {
        sideFirstTest();
        leastRotationTest();
        biggestClusterTest();
    }
}
