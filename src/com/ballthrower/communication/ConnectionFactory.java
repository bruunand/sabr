package com.ballthrower.communication;

/* Factory pattern has been implemented to allow for easy construction of more complex connections in the future. */
public interface ConnectionFactory
{
    Connection createInstance();
}