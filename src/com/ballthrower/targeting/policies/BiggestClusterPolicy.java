package com.ballthrower.targeting.policies;

import com.ballthrower.targeting.ITargetContainer;
import com.ballthrower.targeting.TargetBox;
import com.ballthrower.utilities.ArrayUtil;

import java.util.*;

public class BiggestClusterPolicy extends Policy
{
    private static final Random Random = new Random();

    private final static byte Centroids = 2;
    private final static int Iterations = 10;

    private Centroid[] _centroids = new Centroid[Centroids];

    @Override
    public TargetBox selectTargetBox(ITargetContainer targetContainer)
    {
        if (targetContainer.getTargetCount() == 0)
            return null;
        else if (targetContainer.getTargetCount() <= 2)
            /* If there are only two targets, it makes no sense to use clustering.
            *  In that case, we default to the LeastRotationPolicy. */
            return new LeastRotationPolicy().selectTargetBox(targetContainer);


        /* Select initial guesses. As k = 2, choose the left- and rightmost data points. */
        TargetBox[] targets = targetContainer.cloneTargets();
        ArrayUtil.sort(targets, new AbsolutePositionComparator());
        _centroids[0] = new Centroid(targets[0].getXPosition());
        _centroids[1] = new Centroid(targets[targets.length - 1].getXPosition());

        /* Run clustering for preset amount of iterations. */
        for (int iteration = 0; iteration < Iterations; iteration++)
        {
            /* Each iteration begins with clearing the targets assigned to all centroids.
            *  We don't do this in the end of the iteration because we need the targets. */
            for (Centroid centroid : _centroids)
                centroid.clearTargets();

            /* For each target, find the centroid it has the shortest euclidean distance to. */
            for (byte targetIndex = 0; targetIndex < targetContainer.getTargetCount(); targetIndex++)
            {
                TargetBox target = targetContainer.getTarget(targetIndex);

                /* For each target, sort the array of centoids using the centroid distance comparator. */
                ArrayUtil.sort(this._centroids, new CentroidDistanceComparator(target));

                /* The first element in the sorted array is the centroid that this target is closest to. */
                this._centroids[0].assignTarget(target);
            }

            /* Now all targets are assigned. We need to update all centroids. */
            for (Centroid centroid : _centroids)
                centroid.update();
        }

        /* Sort clusters by those with the most amount.
        *  The last element has the most targets. */
        ArrayUtil.sort(this._centroids, new CentroidSizeComparator());

        return this._centroids[this._centroids.length - 1].getRandomTarget();
    }

    class CentroidSizeComparator implements Comparator<Centroid>
    {
        @Override
        public int compare(Centroid first, Centroid second)
        {
            return ((Integer) first.getTargetCount()).compareTo(second.getTargetCount());
        }
    }

    class CentroidDistanceComparator implements Comparator<Centroid>
    {
        private final TargetBox _target;

        CentroidDistanceComparator(TargetBox target)
        {
            this._target = target;
        }

        @Override
        public int compare(Centroid first, Centroid second)
        {
            Double firstDistance  = first.distanceTo(this._target);
            Double secondDistance = second.distanceTo(this._target);

            return firstDistance.compareTo(secondDistance);
        }
    }

    class Centroid
    {
        private short _x;
        private List<TargetBox> _assignedTargets = new ArrayList<>();

        Centroid(short x)
        {
            this._x = x;
        }

        double distanceTo(TargetBox box)
        {
            return (int) Math.pow(this._x - box.getXPosition(), 2);
        }

        void update()
        {
            int sum = _x;

            /* Add each x-position of assigned target boxes to the sum. */
            for (TargetBox box : _assignedTargets)
                sum += box.getXPosition();

            /* Get the mean of the data points (including the centroid). */
            this._x = (short) (sum / (_assignedTargets.size() + 1));
        }

        void assignTarget(TargetBox target)
        {
            this._assignedTargets.add(target);
        }

        void clearTargets()
        {
            this._assignedTargets.clear();
        }

        TargetBox getRandomTarget()
        {
            if (this._assignedTargets.size() == 0)
                return null;

            return this._assignedTargets.get(Random.nextInt(this._assignedTargets.size()));
        }

        int getTargetCount()
        {
            return this._assignedTargets.size();
        }
    }
}