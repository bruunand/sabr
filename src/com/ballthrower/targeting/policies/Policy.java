package com.ballthrower.targeting.policies;

import com.ballthrower.targeting.ITargetContainer;
import com.ballthrower.targeting.TargetBox;

public abstract class Policy
{
    public abstract TargetBox getTargetBox(ITargetContainer targetContainer);
}