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

    public TargetBox[] getBoxes()
    {
        return _boxes;
    }

    public float[] getHeights()
    {
        float[] heights = new float[getSampleCount()];
        for (int i = 0; i < getSampleCount(); i++)
        {
            heights[i] = getBoxes()[i].getHeight();
        }

        return heights;
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