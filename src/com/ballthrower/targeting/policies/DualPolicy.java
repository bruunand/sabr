package com.ballthrower.targeting.policies;

import com.ballthrower.targeting.ITargetContainer;
import com.ballthrower.targeting.TargetBox;

public class DualPolicy extends Policy
{

    private boolean _passed = false;

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

        if(_passed == false)
        {
            _passed = true;
            return new BiggestClusterPolicy().selectTargetBox(targetContainer);
        }

        return new ClosestDirectionPolicy().selectTargetBox(targetContainer);
    }
}
