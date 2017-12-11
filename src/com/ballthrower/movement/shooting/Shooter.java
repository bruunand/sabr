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
    private RegulatedMotor regMotor;

    //private static final byte Gears = 3;
    private final boolean Direction = false;
    //private static final int[] gearSizes = {40, 24};

    public Shooter(MotorPort[] motors)
    {
        super(new NXTMotor(motors[0]), new NXTMotor(motors[1]), 4.630f);
        regMotor = new NXTRegulatedMotor(motors[0]);
    }

    /**
     * Calculate the power needed to shoot a specific distance, defined by battery power.
     * Assume linear relation between distance and motor power required.
     * @param distance the distance to shoot.
     * @return the motor power needed.
     */
    private int getPowerLinear(float distance)
    {
        /*
        * Distance = 1.039 * Power + 37.43 (r^2 = 0.9948)
        *                   <=>
        * Power = (Distance / 1.039) - 37.43
        *
        * */
        int theoreticalMaxSpeed = 900; /* 9V * approx. 100 */
        double compensationFactor = theoreticalMaxSpeed / regMotor.getMaxSpeed();
        return (int)(((distance / 1.039) - 37.43) * compensationFactor);
    }

    /**
     *
     * @return
     */
    /*private double getGearFactor()
    {
        return Math.pow(gearSizes[0]/gearSizes[1], Gears);
    }*/

    public void shootDistance(float distance)throws OutOfRangeException
    {
        int power = getPowerLinear(distance);
        LCD.drawString("Power:" + power, 0, 2);
        LCD.drawString("Dist:" + distance, 0, 3);

        /* Check if target is out of range. */
        if (power > 100)
            throw new OutOfRangeException("Target too far.");
        else if (power < 50)
            throw new OutOfRangeException("Target too close.");

        /* Run motors. */
        int degrees = (int)(180 / getGearRatio());

        super.startMotors(power, Direction);
        super.waitWhileTurning(degrees);
        super.resetTacho();

        resetMotors();
    }

    /**
     * Run motor at a slow speed to stop at tension stick
     */
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

    private double getInitialVelocity(float distance)
    {
        // Calculate and return the required initial velocity given the target distance, GRAVITY and departure angle.
        return Math.sqrt((distance * GRAVITY)/Math.sin(2*Math.toRadians(DEPARTURE_ANGLE)));
    }

    private int getPower(double velocity)
    {
        int power = (int)(((velocity) - OFFSET)/FACTOR);
        LCD.drawString("Pow: "+ power, 0, 1);

        double compensationFactor = 800 / regMotor.getMaxSpeed();
        power = (int)(power * compensationFactor);

        return power;
    }

     /*private static void runMotors(int power)
    {
        super.startMotors(power, Gears % 2 == 0);
        // ready motors
        motorA.setPower(power);
        motorB.setPower(power);
        // start motors.
        if (Gears % 2 == 0)
        {
            motorA.forward();
            motorB.forward();
        }
        else
        {
            motorA.backward();
            motorB.backward();
        }
    }*/

    /*private static void stopMotors()
    {
        motorA.stop();
        motorB.stop();
    }*/

    /*private static void waitMs(long time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch (Exception ex)
        {
        }
    }*/
}
