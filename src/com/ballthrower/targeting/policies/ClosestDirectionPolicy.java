package com.ballthrower.targeting.policies;

import com.ballthrower.targeting.ITargetContainer;
import com.ballthrower.targeting.TargetBox;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestDirectionPolicy extends Policy
{
    @Override
    public TargetBox getTargetBox(ITargetContainer targetContainer)
    {
        if (targetContainer.getTargetCount() == 0)
            return null;

        /* We are not allowed to directly access the array of targets, so we clone the array. */
        TargetBox[] clonedArray = targetContainer.cloneTargets();

        /* This policy sorts targets by their middle x-position.
           We want the lowest of such values, as that is the target requiring the least rotation.
         */
        Arrays.sort(clonedArray, new DistanceComparator(targetContainer));

        return clonedArray[0];
    }

    class DistanceComparator implements Comparator<TargetBox>
    {
        private final float _frameMiddle;

        DistanceComparator(ITargetContainer container)
        {
            this._frameMiddle = container.getFrameWidth() / 2;
        }

        @Override
        public int compare(TargetBox first, TargetBox second)
        {
            Float firstValue  = Math.abs(first.getMiddleX() - this._frameMiddle);
            Float secondValue = Math.abs(second.getMiddleX() - this._frameMiddle);

            return firstValue.compareTo(secondValue);
        }
    }
}