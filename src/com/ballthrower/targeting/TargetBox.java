package com.ballthrower.targeting;

public class TargetBox
{
    private float _height;
    private float _width;
    private short _xPosition;

    public float getHeight()
    {
        return _height;
    }

    public float getWidth()
    {
        return _width;
    }

    public short getXPosition()
    {
        return _xPosition;
    }

    public TargetBox(float height, float width, short xPosition)
    {
        _height = height;
        _width = width;
        _xPosition = xPosition;
    }
}
