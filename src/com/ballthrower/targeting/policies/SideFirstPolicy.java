package com.ballthrower.targeting.policies;

import com.ballthrower.targeting.ITargetContainer;
import com.ballthrower.targeting.TargetBox;

import java.util.Arrays;
import java.util.Comparator;

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

        /* This policy selects the left or rightmost target.
         * We use an absolute position comparator for this.
         * Since it sorts in ascending order, we need to reverse the array if right side is chosen.
         */
        TargetBox[] clonedArray = targetContainer.cloneTargets();
        if (this._side == Side.Left)
            Arrays.sort(clonedArray, new AbsolutePositionComparator());
        else
            Arrays.sort(clonedArray, new AbsolutePositionComparator().reversed());

        return clonedArray[0];
    }

    class AbsolutePositionComparator implements Comparator<TargetBox>
    {
        @Override
        public int compare(TargetBox first, TargetBox second)
        {
            return ((Float) first.getMiddleX()).compareTo(second.getMiddleX());
        }
    }

    enum Side
    {
        Left,
        Right
    }
}