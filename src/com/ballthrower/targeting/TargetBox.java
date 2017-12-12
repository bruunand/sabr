package com.ballthrower.targeting;

public class TargetBox
{
    private short _height;
    private short _width;
    private short _xPosition;

    public short getHeight()
    {
        return _height;
    }

    public short getWidth()
    {
        return _width;
    }

    public short getXPosition()
    {
        return _xPosition;
    }

    public float getMiddleX()
    {
        return getXPosition() + getWidth() / 2;
    }

    public TargetBox(short height, short width, short xPosition)
    {
        _height = height;
        _width = width;
        _xPosition = xPosition;
    }

    @Override
    public String toString()
    {
        return "X: " + this._xPosition + ", Width: " + this._width + ", Height: " + this._height;
    }
}
