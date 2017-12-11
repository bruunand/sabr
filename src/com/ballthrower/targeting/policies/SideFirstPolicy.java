package com.ballthrower.targeting.policies;

import com.ballthrower.targeting.ITargetContainer;
import com.ballthrower.targeting.TargetBox;
import com.ballthrower.utilities.ArrayUtil;

import java.util.Arrays;

public class SideFirstPolicy extends Policy
{
    private final Side _side;

    public SideFirstPolicy(Side side)
    {
        this._side = side;
    }

    @Override
    public TargetBox selectTargetBox(ITargetContainer targetContainer)
    {
        if (targetContainer.getTargetCount() == 0)
            return null;
        else if (targetContainer.getTargetCount() == 1)
            return targetContainer.getTarget((byte) 0);

        /* This policy selects the left or rightmost target. */
        TargetBox[] clonedArray = targetContainer.cloneTargets();
        ArrayUtil.sort(clonedArray, new AbsolutePositionComparator());

        if (this._side == Side.Left)
            return clonedArray[0];
        else
            return clonedArray[clonedArray.length - 1];
    }

    enum Side
    {
        Left,
        Right
    }
}