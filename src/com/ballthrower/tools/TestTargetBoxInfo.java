package com.ballthrower.tools;

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

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        int numObjects = getTargetBoxInfo().getTargets().length;
        sb.append("Number of objects: " + numObjects);
        for (int i = 0; i < numObjects; i++)
        {
            sb.append("Target #" + i+1 + ":");
            sb.append("Real distance: " + getRealDistance());
            sb.append("Height: " + getTargetBoxInfo().getTargets()[i].getHeight());
            sb.append("Width: " + getTargetBoxInfo().getTargets()[i].getWidth());
            sb.append("xPos: " + getTargetBoxInfo().getTargets()[i].getXPosition());
        }

        return sb.toString();
    }

}
