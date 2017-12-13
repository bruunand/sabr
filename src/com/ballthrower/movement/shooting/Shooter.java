package com.ballthrower.movement.shooting;

import java.lang.Math;

import com.ballthrower.exceptions.OutOfRangeException;

import com.ballthrower.movement.MotorController;
import lejos.nxt.*;
import lejos.robotics.RegulatedMotor;

/**
 * Created by Thomas Buhl on 17/10/2017.
 */
public class Shooter extends MotorController implements IShooter
{
    private static final int GRAVITY = 980;
    private static final int DEPARTURE_ANGLE = 51;
    private static final float FACTOR = 9.7095f;
    private static final int OFFSET = -415;
    private static final float cameraHeight = 52.7f;
    private RegulatedMotor regMotor;

    /* DEBUGGING */
    public float rawPower = 0;
    public float compPower = 0;
    public float compFactor = 0;

    private final boolean Direction = false;

    public Shooter(MotorPort[] motors)
    {
        super(new NXTMotor(motors[0]), new NXTMotor(motors[1]), 4.630f);
        regMotor = new NXTRegulatedMotor(motors[0]);
    }

    /**
     * Calculate the power needed to shoot a specific distance, account for battery power.
     * Assume log relation between distance and motor power required.
     * @param distance the distance to shoot.
     * @return the motor power needed.
     */
    private int getPowerLogarithmic(float distance)
    {
        /*
         * distance = 201.16 + 79.544 * ln(power)
         *                <=>
         * power = e^( (distance+201.16) / 79.544)
         *
         */
        float correctedDistance = distance + 4.5f; /* radius of the cups */
        float exponent = (correctedDistance + 201.16f) / 79.544f;
        float power = (float)Math.pow(Math.E, exponent);

        int theoreticalMaxSpeed = 900; /* 9V * approx. 100 */
        float compensationFactor = theoreticalMaxSpeed / regMotor.getMaxSpeed();

        rawPower = power;
        compFactor = compensationFactor;
        compPower = power * compensationFactor;

        return (int)(power * compensationFactor);
    }

    private int getPowerLinear(float distance)
    {
        /* distance=  78.102 + 0.807 * power
         * giving...
         * power = (distance - 78.102) / 0.802
         * */

        float correctedDistance = distance + 4.5f; /* Radius of the cup */
        correctedDistance += 11.5f; /* Distance from box edge to camera */
        float rawPower = (correctedDistance - 78.102f) / 0.802f;

        int theoreticalMaxSpeed = 900; /* 9V * approx. 100 */
        float compensationFactor = theoreticalMaxSpeed / regMotor.getMaxSpeed();

        return (int)(rawPower * compensationFactor);
    }


    public void shootDistance(float distance)throws OutOfRangeException
    {
        int power = getPowerLinear(distance);
        LCD.drawString("Power:" + power, 0, 2);
        LCD.drawString("Dist:" + distance, 0, 3);

        // Check if target is out of range
        if (power > 100)
            throw new OutOfRangeException("Target too far.");
        else if (power < 50)
            throw new OutOfRangeException("Target too close.");

        // Run motors
        int degrees = (int)(180 / getGearRatio());

        super.startMotors(power, Direction);
        super.waitWhileTurning(degrees);
        super.resetTacho();

        resetMotors();
    }

    private void resetMotors()
    {
        /* Move in opposite direction */
        super.startMotors(15, !Direction);

        /* 180 degrees should be enough */
        waitWhileTurning((int)(180 / getGearRatio()));

        /* Stop motors, reset tacho count */
        super.stopMotors();
        super.resetTacho();
    }

}
