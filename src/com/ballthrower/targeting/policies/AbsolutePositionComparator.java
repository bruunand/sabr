package com.ballthrower.targeting.policies;

import com.ballthrower.targeting.TargetBox;
import java.util.Comparator;

public class AbsolutePositionComparator implements Comparator<TargetBox>
{
    @Override
    public int compare(TargetBox first, TargetBox second)
    {
        return ((Float) first.getMiddleX()).compareTo(second.getMiddleX());
    }
}