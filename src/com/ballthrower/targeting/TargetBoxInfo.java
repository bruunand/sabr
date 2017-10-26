package com.ballthrower.targeting;

public class TargetBoxInfo implements ITargetBoxInfo
{
    TargetBoxInfo(int samples)
    {
        this._samples = samples;
        _boxHeights = new float[samples];
        _xPositions = new int[samples];
    }

    private int _samples;
    private float[] _boxHeights;
    private int[] _xPositions;
    private float _frameMid;

    // Todo: Fix naming conventions
    public void SetHeight(int index, float val)
    {
        _boxHeights[index] = val;
    }
    public void SetXTopPos(int index, int val)
    {
        _xPositions[index] = val;
    }
    public void SetFrameMid(int val)
    {
        _frameMid = val;
    }

    public float[] GetHeightList() { return _boxHeights; }
    public float GetHeight(int index)
    {
        return _boxHeights[index];
    }
    public int GetXTopPos(int index)
    {
        return _xPositions[index];
    }
    public float getFrameMid()
    {
        return _frameMid;
    }
    public int getSamples()
    {
        return _samples;
    }
}