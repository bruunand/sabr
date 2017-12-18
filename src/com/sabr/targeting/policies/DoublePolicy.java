package com.sabr.targeting.policies;

import com.sabr.targeting.ITargetContainer;
import com.sabr.targeting.TargetBox;

public class DoublePolicy extends Policy
{
    private Policy _internalPolicy;
    private boolean _passed = false;

    DoublePolicy(Policy initialPolicy)
    {
        this._internalPolicy = initialPolicy;
    }

    @Override
    public TargetBox selectTargetBox(ITargetContainer targetContainer)
    {

        if (targetContainer.getTargetCount() == 0)
            return null;
        else if (targetContainer.getTargetCount() == 1)
            return targetContainer.getTarget((byte) 0);

        /* This policy selects a random target on first pass and then calibrates the aim on the
            following passes by selecting the closest target.
         */
        if (!_passed)
        {
            _passed = true;

            TargetBox box = _internalPolicy.selectTargetBox(targetContainer);
            _internalPolicy = new LeastRotationPolicy();
            return box;
        }

        return _internalPolicy.selectTargetBox(targetContainer);
    }
}
