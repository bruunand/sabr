package com.ballthrower.targeting;

public interface ITargetBoxInfo
{
    float[] getHeightList();
    float getHeight(byte index);
    float getWidth(byte index);
    int getXTopPos(byte index);
    short getFrameWidth();
    byte getSampleCount();
}