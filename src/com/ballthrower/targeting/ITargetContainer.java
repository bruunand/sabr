package com.ballthrower.targeting;

public interface ITargetContainer
{
    byte getTargetCount();

    TargetBox[] cloneTargets();

    TargetBox getTarget(byte index);
    void setTarget(byte index, TargetBox target);

    short getFrameWidth();
    void setFrameWidth(short frameWidth);
}