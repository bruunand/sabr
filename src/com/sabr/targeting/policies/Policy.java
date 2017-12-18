package com.sabr.targeting.policies;

import com.sabr.targeting.ITargetContainer;
import com.sabr.targeting.TargetBox;

public abstract class Policy
{
    public abstract TargetBox selectTargetBox(ITargetContainer targetContainer);
}