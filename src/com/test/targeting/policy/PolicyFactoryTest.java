package com.test.targeting.policy;

import com.ballthrower.exceptions.AssertException;
import com.ballthrower.targeting.policies.LeastRotationPolicy;
import com.ballthrower.targeting.policies.PolicyFactory;
import com.ballthrower.targeting.policies.SideFirstPolicy;
import com.test.NXTAssert;
import com.test.Test;

/**
 * Created by Anders Brams on 12/12/2017.
 */
public class PolicyFactoryTest extends Test {

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

    @Override
    public void runAllTests() throws AssertException {

    }
}
