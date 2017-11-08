package com.ballthrower.abortion;

public interface IAbortable
{
    void abort(AbortCode code);
    void abort(AbortCode code, String message);
}