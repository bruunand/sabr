package Tools;

import com.ballthrower.targeting.TargetBoxInfo;

/**
 * Normal TBI with real distance
 */
public class TestTargetBoxInfo
{
    private float _realDistance;
    public float getRealDistance() {return _realDistance;}

    private TargetBoxInfo _targetBoxInfo;
    public TargetBoxInfo getTargetBoxInfo() { return _targetBoxInfo; }

    public TestTargetBoxInfo(TargetBoxInfo targetBoxInfo, float realDistance)
    {
        _targetBoxInfo = targetBoxInfo;
        _realDistance = realDistance;
    }
}
