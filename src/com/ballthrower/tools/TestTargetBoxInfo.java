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
        sb.append("Number of objects: " + numObjects+ ":\n");

        for (int i = 0; i < numObjects; i++)
        {
            sb.append("Target #" + i + ":\n");
            sb.append("Real distance: " + getRealDistance() + ":\n");
            sb.append("Height: " + getTargetBoxInfo().getTargets()[i].getHeight() + ":\n");
            sb.append("Width: " + getTargetBoxInfo().getTargets()[i].getWidth() + ":\n");
            sb.append("xPos: " + getTargetBoxInfo().getTargets()[i].getXPosition() + ":\n");
        }

        return sb.toString();
    }

}
