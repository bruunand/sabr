package com.test;

import com.sabr.exceptions.AssertException;

public abstract class Test
{
    abstract public void runAllTests() throws AssertException;
}