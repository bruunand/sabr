package com.ballthrower.targeting;

public class TargetBoxInfo implements ITargetBoxInfo
{
    private byte _sampleCount;
    private TargetBox[] _boxes;

    private short _frameWidth;

    public TargetBoxInfo(byte sampleCount)
    {
        this._sampleCount = sampleCount;
        this._boxes = new TargetBox[sampleCount];
    }

    public TargetBox[] getTargets()
    {
        return _boxes;
    }

    public byte getSampleCount()
    {
        return _sampleCount;
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