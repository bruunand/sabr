package com.test;

/**
 * Exception used for representing assert errors in unit testing
 */
public class AssertException extends Exception
{
    public final String message;
    public final String methodName;

    public AssertException(String _message, String _methodName)
    {
        message = _message;
        methodName = _methodName;
    }
}
