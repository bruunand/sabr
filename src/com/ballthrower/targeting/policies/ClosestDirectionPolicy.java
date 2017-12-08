import com.ballthrower.targeting.TargetBox;
import com.ballthrower.targeting.TargetContainer;
import com.ballthrower.targeting.policies.Policy;

public class ClosestDirectionPolicy extends Policy
{
    @Override
    public TargetBox getTargetBox(TargetContainer targetContainer)
    {
        if (targetContainer.getTargetCount() == 0)
            return null;

    }
}