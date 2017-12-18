package com.sabr.exceptions;

public class UnknownPacketException extends Exception
{
    public UnknownPacketException(String message)
    {
        super(message);
    }
}