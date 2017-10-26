package com.ballthrower.targeting;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;

public class DistanceCalculator implements IDistanceCalculateable
{
    private static final float _targetHeight = 9;

    private static final float _focalLengthHeight = 142.5f *29.5f / _targetHeight;

    @Override
    public float calculateDistance(ITargetBoxInfo target)
    {
        float[] heightList = target.GetHeightList();

        float median = getMedian(heightList);

        float maxDeviance = median * 0.05f;
        float deviance = 0;
        ArrayList<Float> tmp = new ArrayList<>();

        for(int i = 0; i < heightList.length; i++)
        {
            deviance = abs(median - heightList[i]);

            if(deviance <= maxDeviance)
                tmp.add(heightList[i]);
        }

        float[] refinedHeightList = new float[tmp.size()];

        for(int i = 0; i < tmp.size(); i++) {
            refinedHeightList[i] = tmp.get(i);
        }

        if(refinedHeightList.length == 0)
            // Query the camera for a new set of data.
            return -1;

        median = getMedian(refinedHeightList);

        return _focalLengthHeight * _targetHeight / median;
    }

    private float getMedian(float[] arr) {
        float median;
        Arrays.sort(arr);
        if (arr.length % 2 == 0)
            median = (arr[arr.length/2] + arr[arr.length/2 - 1])/2;
        else
            median = arr[arr.length/2];

        return median;
    }

}