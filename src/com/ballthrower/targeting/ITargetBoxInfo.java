package com.ballthrower.targeting;

public interface ITargetBoxInfo
{
    byte getSampleCount();

    TargetBox[] getTargets();

    short getFrameWidth();
    void setFrameWidth(short frameWidth);
}