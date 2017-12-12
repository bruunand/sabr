package com.test;

import com.ballthrower.exceptions.AssertException;

public abstract class Test
{
    abstract void runAllTests() throws AssertException;
}