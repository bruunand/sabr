package com.ballthrower.targeting.policies;

import com.ballthrower.targeting.TargetBox;
import com.ballthrower.targeting.TargetContainer;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestDirectionPolicy extends Policy
{
    @Override
    public TargetBox getTargetBox(TargetContainer targetContainer)
    {
        if (targetContainer.getTargetCount() == 0)
            return null;

        /* We are not allowed to directly access the array of targets, so we clone the array. */
        TargetBox[] clonedArray = targetContainer.cloneTargets();

        /* This policy sorts targets by their middle x-position.
           We want the lowest of such values, as that is the target requiring the least rotation.
         */
        Arrays.sort(clonedArray, new DistanceComparator());

        return clonedArray[0];
    }

    public class DistanceComparator implements Comparator<TargetBox>
    {
        @Override
        public int compare(TargetBox first, TargetBox second)
        {
            return ((Float) first.getMiddleX()).compareTo(second.getMiddleX());
        }
    }
}