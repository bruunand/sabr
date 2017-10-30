package com.ballthrower.targeting;

public class TargetBoxInfo implements ITargetBoxInfo
{
    private byte _sampleCount;
    private float[] _boxHeights;
    private short[] _xPositions;
    private short _frameWidth;

    public TargetBoxInfo(byte sampleCount)
    {
        this._sampleCount = sampleCount;
        this._xPositions = new short[sampleCount];
        this._boxHeights = new float[sampleCount];
    }

    public void setHeight(byte index, float val)
    {
        this._boxHeights[index] = val;
    }

    public void setXTopPos(byte index, short val)
    {
        this._xPositions[index] = val;
    }

    public void setFrameWidth(short val)
    {
        this._frameWidth = val;
    }

    public float[] getHeightList() { return this._boxHeights; }

    public float getHeight(byte index)
    {
        return this._boxHeights[index];
    }

    public int getXTopPos(byte index)
    {
        return this._xPositions[index];
    }

    public short getFrameWidth()
    {
        return this._frameWidth;
    }

    public byte getSampleCount()
    {
        return this._sampleCount;
    }
}