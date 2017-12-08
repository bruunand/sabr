package com.ballthrower.targeting;

public class DirectionCalculator implements IDirectionCalculateable
{
    /** Dependant on the type of camera.
     * Currently using: Logitech
     * Describes how many pixels cover one degree of
     * vision on the horizontal axis. */
    private static final float _degreesPerPixel = 0.133F;
    private float _frameMiddle;

    public DirectionCalculator(ITargetContainer targetContainer) {
        _frameMiddle = targetContainer.getFrameWidth() / 2;
    }

    /** Returns the number of degrees that should be turned
     * in order to face the target. Return value can be both
     * negative and positive in order to describe the direction
     * of the turn. */
    public float calculateDirection(TargetBox target)
    {
        float boxOffset = target.getXPosition() + target.getWidth() / 2;
        return (_frameMiddle - boxOffset) * _degreesPerPixel;
    }
}