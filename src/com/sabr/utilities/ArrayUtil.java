package com.sabr.utilities;

import java.util.Comparator;

public class ArrayUtil
{
    private static final int INSERTIONSORT_THRESHOLD = 7;

    public static <T> void sort(T[] array, Comparator<T> comparator)
    {
        T[] arrayCopy = (T[]) clone(array);
        mergeSort(arrayCopy, array, 0, array.length, 0, comparator);
    }

    public static Object[] clone(Object[] source)
    {
        Object[] clonedArray = new Object[source.length];
        System.arraycopy(source, 0, clonedArray, 0, clonedArray.length);
        return clonedArray;
    }

    /* Source: java.util.Arrays. */
    private static <T> void swap(T[] x, int a, int b)
    {
        T temp = x[a];
        x[a] = x[b];
        x[b] = temp;
    }

    /* Source: java.util.Arrays. */
    private static <T> void mergeSort(T[] src, T[] dest, int low, int high, int off, Comparator<T> c)
    {
        int length = high - low;

        // Insertion sort on smallest arrays
        if (length < INSERTIONSORT_THRESHOLD)
        {
            for (int i=low; i<high; i++)
                for (int j=i; j>low && c.compare(dest[j-1], dest[j])>0; j--)
                    swap(dest, j, j-1);
            return;
        }

        // Recursively sort halves of dest into src
        int destLow  = low;
        int destHigh = high;
        low  += off;
        high += off;
        int mid = (low + high) >>> 1;
        mergeSort(dest, src, low, mid, -off, c);
        mergeSort(dest, src, mid, high, -off, c);

        // If list is already sorted, just copy from src to dest.  This is an
        // optimization that results in faster sorts for nearly ordered lists.
        if (c.compare(src[mid-1], src[mid]) <= 0)
        {
            System.arraycopy(src, low, dest, destLow, length);
            return;
        }

        // Merge sorted halves (now in src) into dest
        for(int i = destLow, p = low, q = mid; i < destHigh; i++)
        {
            if (q >= high || p < mid && c.compare(src[p], src[q]) <= 0)
                dest[i] = src[p++];
            else
                dest[i] = src[q++];
        }
    }
}