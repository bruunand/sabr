package com.ballthrower.targeting;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;

// Calculates the distance to the target object based on
// triangle similarity using the height of the target object.
public class DistanceCalculator implements IDistanceCalculateable
{
    /** Physical height, manually measured */
    private static final float _targetHeight = 11.5f;

    /** Pixel height at distance = _knownRealDistance */
    private static final float _knownHeight = 120.0f;

    /** Real distance in centimeters */
    private static final float _knownRealDistance = 110.2f;

    /** Focal length is the distance between the image plane and lens of the camera
      * Used for calculating the distance to an object */
    private static final float _focalLengthHeight = _knownHeight * _knownRealDistance / _targetHeight;

    /**
     * Returns a float representing the distance to the target object.
     * It assumes that the target object is close to directly in front
     * of the image capturing device. For this reason rotation should
     * be performed before attempting to calculate the distance.

     * Input: An instance of a class implementing ITargetBoxInfo
     * which contains sample data received from a data gathering
     * device.

     * Computation:
     *  - Find the median height from the sample list
     *  - Remove samples deviating more than 5% from the median
     *  - Get median from the sample list without outliers
     *  - Calculate the distance using the focal length
     */
    @Override
    public float calculateDistance(ITargetBoxInfo target)
    {
        /* Iterate over all samples; if none exist, return */
        if (target.getSampleCount() == 0)
            return Float.POSITIVE_INFINITY;

        /* Store heights in an array */
        short[] heights = new short[target.getSampleCount()];
        for (int i = 0; i < target.getSampleCount(); i++)
            heights[i] = target.getTargets()[i].getHeight();

        /* Calculate median */
        short median = getMedian(heights);

        /* Remove outliers from the list of heights given
         * the current median */
        float[] refinedHeightList = removeOutliers(heights, median);

        /* If every sample was an outlier... */
        if(refinedHeightList.length == 0)
            // Query the camera for a new set of data.
            return -1;

        /* Recalculate median based on refined heights */
        median = getMedian(refinedHeightList);

        /* Calculate and return the distance.
         * See report for triangle similarity method calculation method. */

        return _focalLengthHeight * _targetHeight / median;
    }

    /** Utility method for converting an ArrayList
     *  to an array. */
    private float[] convertToArray(ArrayList<Float> arr)
    {
        float[] newArray = new float[arr.size()];
        int iterations = arr.size();
        for(int i = 0; i < iterations; i++)
        {
            newArray[i] = arr.get(i);
        }
        return newArray;
    }

    /** Sorts the array of floats, and returns the middle
     *  element. If number of elements is even, return mean
     *  of the two middle-most elements. */
    public float getMedian(float[] arr)
    {
        float median;
        int arrayLength = arr.length;

        Arrays.sort(arr);
        if (arrayLength % 2 == 0)
            median = (arr[arrayLength  / 2] + arr[arrayLength  / 2 - 1]) / 2;
        else
            median = arr[arrayLength  / 2];

        return median;
    }

    /** Removes samples deviating more than 5% from the median. */
    public float[] removeOutliers(float[] heights, float median)
    {
        ArrayList<Float> toReturn = new ArrayList<Float>();
        float maxDeviance = median * 0.05f;
        float deviance = 0;

        for (float aHeightList : heights)
        {
            deviance = abs(median - aHeightList);

            if (deviance <= maxDeviance)
                toReturn.add(aHeightList);
        }

        return convertToArray(toReturn);
    }
}