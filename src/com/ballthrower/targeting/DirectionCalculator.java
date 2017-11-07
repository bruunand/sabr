package com.ballthrower.targeting;

public class DirectionCalculator implements IDirectionCalculateable
{
    private static final float _degreesPerPixel = 0.133F;

    public float calculateDirection(ITargetBoxInfo target)
    {
        return calculateMeanPixelDistance(target) * _degreesPerPixel;
    }

    public float calculateMeanPixelDistance(ITargetBoxInfo targets)
    {
        byte iterations = targets.getSampleCount();
        if (iterations == 0)
            return Float.POSITIVE_INFINITY;

        // Computes the sum of all the box distances to the middle of the frame
        float sumDistances = 0;
        float frameMiddle = targets.getFrameWidth() / 2;
        for (byte i = 0; i < iterations; i++)
        {
            float boxOffset = targets.getBoxes()[i].getXPosition()
                            + targets.getBoxes()[i].getWidth() / 2;
            sumDistances += frameMiddle - boxOffset;
        }

        return sumDistances / iterations;
    }
}