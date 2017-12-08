package com.ballthrower.targeting.policies;

import com.ballthrower.targeting.ITargetContainer;
import com.ballthrower.targeting.TargetBox;

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
            *  In that case, we default to the ClosestDirectionPolicy. */
            return new ClosestDirectionPolicy().selectTargetBox(targetContainer);

        /* Automatically generate centroids. */
        for (byte i = 0; i < Centroids; i++)
            _centroids[i] = (new Centroid(targetContainer.getFrameWidth()));

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

                /* For each target, we need a local list of centroids to sort. */
                Centroid[] localCentroids = this._centroids.clone();

                /* We then sort the array of centroids. */
                Arrays.sort(localCentroids, new CentroidDistanceComparator(target));

                /* The first element in the sorted array is the centroid that this target is closest to. */
                localCentroids[0].assignTarget(target);
            }

            /* Now all targets are assigned. We need to update all centroids. */
            for (Centroid centroid : _centroids)
                centroid.update();
        }

        /* Sort clusters by those with the most amount.
        *  We have reversed its order (making it descending), so the first element has the most targets. */
        Arrays.sort(this._centroids, new CentroidSizeComparator().reversed());

        return this._centroids[0].getRandomTarget();
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

        Centroid(short frameWidth)
        {
            this._x = (short) Random.nextInt(frameWidth);
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