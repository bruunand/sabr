package com.ballthrower.targeting.policies;

import com.ballthrower.targeting.TargetBox;
import com.ballthrower.targeting.TargetBoxInfo;

public abstract class Policy
{
    public abstract TargetBox getTargetBox(TargetBoxInfo targetBoxInfo);
}