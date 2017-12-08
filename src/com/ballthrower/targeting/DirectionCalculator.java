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
    public float calculateDirection(ITargetContainer target)
    {
        return calculateMeanPixelDistance(target) * _degreesPerPixel;
    }

    /** From all the sample target boxes passed as argument, calculates
     * the mean of the distance from the target boxes to the middle of
     * the frame. Output value is given in number of pixels. */
    public float calculateMeanPixelDistance(ITargetContainer targets)
    {
        return 0;
    }
}