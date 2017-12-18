package com.sabr.targeting.policies;

import com.sabr.Robot;
import com.sabr.targeting.ITargetContainer;
import com.sabr.targeting.TargetBox;

public class RandomPolicy extends Policy
{
    @Override
    public TargetBox selectTargetBox(ITargetContainer targetContainer)
    {
        return targetContainer.getTarget((byte) Robot.getInstance().Random.nextInt(targetContainer.getTargetCount()));
    }
}