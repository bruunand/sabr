package com.ballthrower.targeting;

public class DirectionCalculator
{
    /**
     * Dependant on the type of camera.
     * Currently using: Logitech
     * Is the angle from the center point of the camera,
     * to the egde of the cameras field of view.
     */
    private static final float _maxAngle = 33.0F;

    /** Returns the number of degrees that should be turned
     * in order to face the target. Return value can be both
     * negative and positive in order to describe the direction
     * of the turn. */
    public static float calculateDirection(ITargetContainer targetContainer, ITargetBox target)
    {
        /* Calculate frame middle and number of degrees per pixel. */
        float frameMiddle = targetContainer.getFrameWidth() / 2;
        float degreesPerPixel = _maxAngle / frameMiddle;

        /* Measure how far the center of the target is from the middle of the frame. */
        float boxOffset = target.getXPosition() + target.getWidth() / 2;

        /* Use the calculations to calculate the direction. */
        return (frameMiddle - boxOffset) * degreesPerPixel;
    }
}
