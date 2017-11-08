package com.ballthrower.communication;

public class BluetoothConnectionFactory implements ConnectionFactory
{
    public Connection createInstance()
    {
        return new BluetoothConnection();
    }
}