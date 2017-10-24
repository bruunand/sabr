package com.ballthrower.targeting;


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
            int xTopLeft = target.GetXPostion(i);
            float height = target.GetHeight(i);
            float xCentre = xTopLeft + height/2;
            vec2lineX = (frameMid-xCentre);
            sumDistances += (float)sqrt(vec2lineX*vec2lineX)
        }
        int meanDistance = sumDistances/iterations;
        int angle2Rotate = meanDistance * _degreesPerPixel;
        return angle2Rotate;
    }
}