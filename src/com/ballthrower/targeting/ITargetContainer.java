package com.ballthrower.targeting;

public interface ITargetContainer
{
    byte getTargetCount();

    TargetBox[] getTargets();

    short getFrameWidth();
    void setFrameWidth(short frameWidth);
}