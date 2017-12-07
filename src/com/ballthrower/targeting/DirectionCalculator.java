package com.ballthrower.targeting;

public class DirectionCalculator implements IDirectionCalculateable
{
    /** Dependant on the type of camera.
     * Currently using: Logitech
     * Describes how many pixels cover one degree of
     * vision on the horizontal axis. */
    private static final float _degreesPerPixel = 0.133F;

    /** Returns the number of degrees that should be turned
     * in order to face the target. Return value can be both
     * negative and positive in order to describe the direction
     * of the turn. */
    public float calculateDirection(ITargetBoxInfo target)
    {
        return calculateMeanPixelDistance(target) * _degreesPerPixel;
    }

    /** From all the sample target boxes passed as argument, calculates
     * the mean of the distance from the target boxes to the middle of
     * the frame. Output value is given in number of pixels. */
    public float calculateMeanPixelDistance(ITargetBoxInfo targets)
    {
        /* Iterate over all targets. If there are no targets, return
         * value cannot be calculated. */
        byte iterations = targets.getSampleCount();
        if (iterations == 0)
            return Float.POSITIVE_INFINITY;

        float sumDistances = 0;
        float frameMiddle = targets.getFrameWidth() / 2;

        for (byte i = 0; i < iterations; i++)
        {
            float boxOffset = targets.getTargets()[i].getXPosition()
                            + targets.getTargets()[i].getWidth() / 2;

            /* How far is the box from the middle of the frame? */
            sumDistances += frameMiddle - boxOffset;
        }

        /* Mean */
        return sumDistances / iterations;
    }
}