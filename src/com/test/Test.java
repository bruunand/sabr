package com.test;

import com.ballthrower.exceptions.AssertException;

public abstract class Test
{
    abstract public void runAllTests() throws AssertException;
}