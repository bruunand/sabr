package com.ballthrower.targeting.policies;

public final class PolicyFactory
{

    private PolicyFactory()
    {

    }

    public static Policy getPolicy(PolicyType type)
    {

        switch (type)
        {
            case random:
                return new DualPolicy();
            case left_first:
                return new SideFirstPolicy(SideFirstPolicy.Side.Left);
            case right_first:
                return new SideFirstPolicy(SideFirstPolicy.Side.Right);
            case biggest_cluster:
                return new BiggestClusterPolicy();
        }

        return null;
    }

    public enum PolicyType
    {
        random,
        left_first,
        right_first,
        biggest_cluster
    }

}
