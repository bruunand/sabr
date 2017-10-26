package com.ballthrower.targeting;

public interface ITargetBoxInfo
{
    float[] GetHeightList();
    float GetHeight(int index);
    int GetXTopPos(int index);
    float getFrameMid();
    int getSamples();
}