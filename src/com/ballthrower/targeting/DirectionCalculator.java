package com.ballthrower.targeting;


import static java.lang.Math.sqrt;

public class DirectionCalculator implements IDirectionCalculateable
{
    private static final float _degreesPerPixel = 0.1017F;

    // plzz comment thiss
    public float calculateDirection(ITargetBoxInfo target)
    {
        byte iterations = target.getSampleCount();
        float sumDistances = 0;
        float frameMid = target.getFrameWidth() / 2;
        for (byte i = 0; i < iterations; i++)
        {
            int xTopLeft = target.getXTopPos(i);
            float height = target.getHeight(i);
            float xCentre = xTopLeft + height / 2;
            float vec2lineX = (frameMid-xCentre);
            sumDistances += vec2lineX;
        }
        float meanDistance = sumDistances/iterations;
        float angle2Rotate =  meanDistance * _degreesPerPixel;
        return angle2Rotate;
    }
}