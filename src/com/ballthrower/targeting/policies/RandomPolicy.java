package com.ballthrower.targeting.policies;

import com.ballthrower.Robot;
import com.ballthrower.targeting.ITargetContainer;
import com.ballthrower.targeting.TargetBox;

import java.util.Random;

public class RandomPolicy extends Policy
{
    @Override
    public TargetBox selectTargetBox(ITargetContainer targetContainer)
    {
        return targetContainer.getTarget((byte) Robot.getInstance().Random.nextInt(targetContainer.getTargetCount()));
    }
}