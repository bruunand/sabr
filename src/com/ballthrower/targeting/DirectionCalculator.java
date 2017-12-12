package com.ballthrower.targeting;
import lejos.nxt.LCD;

public class DirectionCalculator implements IDirectionCalculateable
{
    /**
     * Dependant on the type of camera.
     * Currently using: Logitech
     * Is the angle from the center point of the camera,
     * to the egde of the cameras field of view.
     */
    private static final float _maxAngle = 26.725F;

    private float _frameMiddle;

    /**
     * Dependent on the max angle
     */
    private float _degreesPerPixel;

    public DirectionCalculator(ITargetContainer targetContainer)
    {
        _frameMiddle = targetContainer.getFrameWidth() / 2;

        _degreesPerPixel = _maxAngle / _frameMiddle;
    }

    /** Returns the number of degrees that should be turned
     * in order to face the target. Return value can be both
     * negative and positive in order to describe the direction
     * of the turn. */
    public float calculateDirection(TargetBox target)
    {
        float boxOffset = target.getXPosition() + target.getWidth() / 2;
        LCD.clear();
        LCD.drawString("Pixels:" + (_frameMiddle - boxOffset), 0, 0);
        return (_frameMiddle - boxOffset) * _degreesPerPixel;
    }
}
