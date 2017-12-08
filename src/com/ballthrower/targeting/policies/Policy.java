package com.ballthrower.targeting.policies;

import com.ballthrower.targeting.TargetBox;
import com.ballthrower.targeting.TargetContainer;

public abstract class Policy
{
    public abstract TargetBox getTargetBox(TargetContainer targetContainer);
}