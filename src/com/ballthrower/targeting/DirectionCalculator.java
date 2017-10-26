package com.ballthrower.targeting;


import static java.lang.Math.sqrt;

public class DirectionCalculator implements IDirectionCalculateable
{
    private static final float _degreesPerPixel = 0.1017F;

    public float CalculateDirection(ITargetBoxInfo target)
    {
        int iterations = target.getSamples();
        float sumDistances = 0;
        float frameMid = target.getFrameMid();
        for(int i=0;i<iterations;++i)
        {
            int xTopLeft = target.GetXTopPos(i);
            float height = target.GetHeight(i);
            float xCentre = xTopLeft + height/2;
            float vec2lineX = (frameMid-xCentre);
            sumDistances += (float)sqrt(vec2lineX*vec2lineX);
        }
        float meanDistance = sumDistances/iterations;
        float angle2Rotate =  meanDistance * _degreesPerPixel;
        return angle2Rotate;
    }
}