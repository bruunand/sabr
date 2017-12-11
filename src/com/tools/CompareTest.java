package com.tools;

import com.ballthrower.utilities.ArrayUtil;
import lejos.nxt.Button;
import lejos.nxt.LCD;

import java.util.Comparator;

public class CompareTest
{
    public static void main(String[] args)
    {
        Example[] examples = new Example[3];
        examples[0] = new Example(0);
        examples[1] = new Example(-2);
        examples[2] = new Example(10);
        ArrayUtil.sort(examples, new FirstComparator());
        LCD.drawString("First:" + examples[0]._x, 0, 0);
        Button.waitForAnyPress();
        ArrayUtil.sort(examples, new SecondComparator());
        LCD.clear();
        LCD.drawString("Second:" + examples[0]._x, 0, 0);
        Button.waitForAnyPress();
    }

    static class Example
    {
        public int _x;


        public Example(int x)
        {
            this._x = x;
        }
    }

    private static class FirstComparator implements Comparator<Example>
    {
        @Override
        public int compare(Example first, Example second)
        {
            return ((Integer) first._x).compareTo(second._x);
        }
    }

    private static class SecondComparator implements Comparator<Example>
    {
        @Override
        public int compare(Example first, Example second)
        {
            return ((Integer) second._x).compareTo(first._x);
        }
    }
}