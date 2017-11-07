package com.ballthrower.targeting;

public interface ITargetBoxInfo
{
    byte getSampleCount();

    TargetBox[] getBoxes();

    float[] getHeights();

    short getFrameWidth();
    void setFrameWidth(short frameWidth);
}