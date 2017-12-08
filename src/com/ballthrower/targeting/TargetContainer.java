package com.ballthrower.targeting;

public class TargetContainer implements ITargetContainer
{
    private byte _targetCount;
    private TargetBox[] _targets;

    private short _frameWidth;

    public TargetContainer(byte targetCount)
    {
        this._targetCount = targetCount;
        this._targets = new TargetBox[targetCount];
    }

    public TargetBox[] cloneTargets()
    {
        return _targets.clone();
    }

    public void setTarget(byte index, TargetBox box)
    {
        _targets[index] = box;
    }

    public TargetBox getTarget(byte index)
    {
        return _targets[index];
    }

    public byte getTargetCount()
    {
        return _targetCount;
    }

    public short getFrameWidth()
    {
        return _frameWidth;
    }

    public void setFrameWidth(short frameWidth)
    {
        _frameWidth = frameWidth;
    }
}