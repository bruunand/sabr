package com.ballthrower.tools;

import com.ballthrower.targeting.TargetContainer;

/**
 * Normal TBI with real distance
 */
public class TestTargetBoxInfo
{
    private float _realDistance;
    public float getRealDistance() {return _realDistance;}

    private TargetContainer _targetContainer;
    public TargetContainer getTargetBoxInfo() { return _targetContainer; }

    public TestTargetBoxInfo(TargetContainer targetContainer, float realDistance)
    {
        _targetContainer = targetContainer;
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
