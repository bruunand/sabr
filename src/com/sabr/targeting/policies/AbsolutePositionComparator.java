package com.sabr.targeting.policies;

import com.sabr.targeting.TargetBox;

import java.util.Comparator;

public class AbsolutePositionComparator implements Comparator<TargetBox>
{
    @Override
    public int compare(TargetBox first, TargetBox second)
    {
        return ((Float) first.getMiddleX()).compareTo(second.getMiddleX());
    }
}
