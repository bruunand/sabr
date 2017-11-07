package com.ballthrower;

import com.ballthrower.communication.BluetoothConnection;
import com.ballthrower.communication.Connection;
import com.ballthrower.communication.ConnectionFactory;
import com.ballthrower.movement.MovementController;
import com.ballthrower.targeting.DirectionCalculator;
import com.ballthrower.targeting.DistanceCalculator;
import lejos.nxt.Button;
import lejos.nxt.MotorPort;

// The Robot class uses the singleton pattern, since only one robot can be used.
public class Robot
{
    private static Robot _robotInstance = new Robot();

    private final DistanceCalculator _distanceCalculator;
    private final DirectionCalculator _directionCalculator;
    private final MovementController _movementController;

    private Connection _connection;

    public static Robot getInstance()
    {
        return Robot._robotInstance;
    }

    private Robot()
    {
        // Set up distance and direction calculator instances.
        this._distanceCalculator = new DistanceCalculator();
        this._directionCalculator = new DirectionCalculator();

        // Set up movement controller with desired motors.
        this._movementController = new MovementController(MotorPort.C, new MotorPort[]{MotorPort.A, MotorPort.B});

        // Add button listeners

    }

    public void awaitConnection(ConnectionFactory connectionFactory)
    {
        // If a connection already exists, close it.
        if (this._connection != null)
        {
            this._connection.closeConnection();
            this._connection = null;
        }

        // Remove the action listener, if it exists. TODO

        // Instantiate the connection and await the connection from host.
        this._connection = connectionFactory.createInstance();
        this._connection.awaitConnection();
    }

    private void addExitListener(Button button)
    {

    }

    private void addActionListener(Button button)
    {

    }

}