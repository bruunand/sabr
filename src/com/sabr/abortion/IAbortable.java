package com.sabr.abortion;

public interface IAbortable
{
    void abort(AbortCode code);
    void abort(AbortCode code, String message);
    void warn(String message);
}