package com.sabr.targeting.policies;

import com.sabr.targeting.ITargetContainer;
import com.sabr.targeting.TargetBox;
import com.sabr.utilities.ArrayUtil;

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

    public enum Side
    {
        Left,
        Right
    }
}