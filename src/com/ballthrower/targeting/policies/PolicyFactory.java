package com.ballthrower.targeting.policies;

public final class PolicyFactory
{
    public static Policy getPolicy(PolicyType type)
    {
        switch (type)
        {
            case Random:
                return new DualPolicy();
            case LeftFirst:
                return new SideFirstPolicy(SideFirstPolicy.Side.Left);
            case RightFirst:
                return new SideFirstPolicy(SideFirstPolicy.Side.Right);
            case BiggestCluster:
				// TODO: Should return dual policy
                return new BiggestClusterPolicy();
			case Nearest:
				return new LeastRotationPolicy();
        }

        return null;
    }

    public enum PolicyType
    {
        Random,
        LeftFirst,
        RightFirst,
        BiggestCluster,
		Nearest
    }
}
