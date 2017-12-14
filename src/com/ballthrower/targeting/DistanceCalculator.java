package com.ballthrower.targeting;

// Calculates the distance to the target object based on
// triangle similarity using the height of the target object.
public class DistanceCalculator
{
    /** Physical height, manually measured */
    private static final float _targetHeight = 12.0f;

    /** Pixel height at distance = _knownRealDistance */
    private static final float _knownHeight = 94.0f;

    /** Real distance in centimeters */
    private static final float _knownRealDistance = 160.0f;

    /** Focal length is the distance between the image plane and lens of the camera
      * Used for calculating the distance to an object */
    private static final float _focalLengthHeight = _knownHeight * _knownRealDistance / _targetHeight;

    /**
     * Returns a float representing the distance to the target object.
     * It assumes that the target object is close to directly in front
     * of the image capturing device. For this reason rotation should
     * be performed before attempting to calculate the distance.

     * Input: An instance of a class implementing ITargetBox

     * Computation:
     *  - Calculate the distance using the focal length
     */
    public static float calculateDistance(ITargetBox target)
    {
        return (_focalLengthHeight * _targetHeight / target.getHeight());
    }

}
