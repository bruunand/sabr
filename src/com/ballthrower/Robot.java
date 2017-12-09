package com.ballthrower;

import com.ballthrower.abortion.AbortCode;
import com.ballthrower.abortion.IAbortable;
import com.ballthrower.communication.Connection;
import com.ballthrower.communication.ConnectionFactory;
import com.ballthrower.communication.packets.Packet;
import com.ballthrower.communication.packets.PacketIds;
import com.ballthrower.communication.packets.TargetInfoRequestPacket;
import com.ballthrower.exceptions.OutOfRangeException;
import com.ballthrower.listeners.ExitButtonListener;
import com.ballthrower.listeners.ShootButtonListener;
import com.ballthrower.movement.aiming.IRotator;
import com.ballthrower.movement.aiming.Rotator;
import com.ballthrower.movement.shooting.IShooter;
import com.ballthrower.movement.shooting.Shooter;
import com.ballthrower.targeting.DirectionCalculator;
import com.ballthrower.targeting.DistanceCalculator;
import com.ballthrower.targeting.ITargetContainer;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.MotorPort;
import lejos.nxt.Sound;

// The Robot class uses the singleton pattern, since only one robot can be used.
public class Robot implements IAbortable
{
    private static Robot _robotInstance = new Robot();

    private static final float TARGET_ANGLE_THRESHOLD = 3.0f;

    private static final Button EXIT_BUTTON = Button.ESCAPE;
    private static final Button SHOOT_BUTTON = Button.ENTER;

    private final DistanceCalculator _distanceCalculator;
    private final DirectionCalculator _directionCalculator;
    private final IShooter _shooter;
    private final IRotator _rotator;

    private TargetingPolicyType _targetingPolicyType = TargetingPolicyType.Nearest;

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

        // Set up movement controllers with desired motors.
        this._rotator = new Rotator(MotorPort.C);
        this._shooter = new Shooter(new MotorPort[]{MotorPort.A, MotorPort.B});
    }

    public void addButtonListeners()
    {
        EXIT_BUTTON.addButtonListener(new ExitButtonListener());
        SHOOT_BUTTON.addButtonListener(new ShootButtonListener());
    }

    public void locateAndShoot()
    {
        while (true)
        {
            ITargetContainer targetInformation = receiveTargetInformation();

            // Calculate the angle to the target object.
            float directionAngle = _directionCalculator.calculateDirection(targetInformation);
            if (Math.abs(directionAngle) > TARGET_ANGLE_THRESHOLD)
            {
                // We are not facing the target, so we must rotate towards it first.
                _rotator.turnDegrees(directionAngle);
            }
            else
            {
                // Target is faced, shoot it.
                Sound.beep(); // TODO: Debug, remove me
                try
                {
                    _shooter.shootDistance(_distanceCalculator.calculateDistance(targetInformation));
                }
                catch (OutOfRangeException ex)
                {
                    LCD.drawString(ex.getMessage(),0,0);
                }
                return;
            }
        }
    }

    private ITargetContainer receiveTargetInformation()
    {
        // Request target information
        this._connection.sendPacket(new TargetInfoRequestPacket());

        // Receive packet with target information
        Packet receivedPacket = this._connection.receivePacket();
        if (receivedPacket.getId() != PacketIds.TargetDirectionRequest)
            this.abort(AbortCode.UNKNOWN_PACKET, "Expected target information.");

        return ((TargetInfoRequestPacket)receivedPacket).getTargetBoxInfo();
    }

    private void closeConnection()
    {
        if (this._connection != null)
        {
            this._connection.closeConnection();
            this._connection = null;
        }
    }

    public void awaitConnection(ConnectionFactory connectionFactory)
    {
        // Close any existing connection
        this.closeConnection();

        // Instantiate the connection and await the connection from
        this._connection = connectionFactory.createInstance(ConnectionFactory.ConnectionType.Bluetooth, this);
        this._connection.awaitConnection();
    }

    public void setTargetingPolicy(TargetingPolicyType policyType)
    {
        this._targetingPolicyType = policyType;
    }

    public void abort(AbortCode code)
    {
        abort(code, null);
    }

    public void abort(AbortCode code, String message)
    {
        // Draw abort message
        LCD.clear();

        LCD.drawString("Robot aborted", 0, 0);
        LCD.drawString("Code: " + code, 0, 1);
        if (message != null && !message.isEmpty())
            LCD.drawString(message, 0, 2);

        // Await key press and exit system fully
        Button.waitForAnyPress();
        System.exit(code.ordinal());
    }
}