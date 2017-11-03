package com.ballthrower.movement.shooting;

import java.lang.Math;
import lejos.nxt.*;
import lejos.robotics.RegulatedMotor;

/**
 * Created by Thomas Buhl on 17/10/2017.
 */
public class Shooter implements IShooter
{
    private static final double g = 9.8;
    private static final int departureAngle = 45;
    private static final double factor = 5.0895;
    private static final double offset = 27.746;

    private static NXTMotor motorA;
    private static NXTMotor motorB;
    private static RegulatedMotor regMotor;

    private static final byte Gears = 3;
    private static final int[] gearSizes = {40, 24};

    private static int maxSpeed = 800;

    public Shooter(MotorPort[] motors)
    {
        motorA = new NXTMotor(motors[0]);
        motorB = new NXTMotor(motors[1]);
        regMotor = new NXTRegulatedMotor(motors[0]);
    }

    private double getInitialVelocity(Double distance)
    {
        // Calculate and return the required initial velocity given the target distance, gravity and departure angle.
        return Math.sqrt((distance * g)/Math.sin(2*Math.toRadians(departureAngle)));
    }

    private int getPower(double velocity)
    {
        int power = (int)((velocity - offset)/factor);

        double compensationFactor = 800 / regMotor.getMaxSpeed();
        power = (int)(power * compensationFactor);

        return power;
    }

    private double getGearFactor()
    {
        return Math.pow(gearSizes[0]/gearSizes[1], Gears);
    }

    public void Shoot(Double distance)
    {
        double initialVelocity = getInitialVelocity(distance);
        int power = getPower(initialVelocity);

        if (power > 100)
        {
            // This is bad! Redo to throw exception!
            LCD.drawString("Target out of range.", 0, 0 );
            return;
        }
        else if (power < 70)
        {
            // This is bad! Redo to throw exception!
            LCD.drawString("Target too close.", 0, 0);
            return;
        }

        int degrees = (int)((1.5*360) / getGearFactor());

        runMotors(power);

        // wait for motors to turn
        while( Math.abs(motorA.getTachoCount()) < degrees){}

        stopMotors();
        waitMs(1000);
        resetMotors();
    }

    private static void runMotors(int power)
    {
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
    }

    private static void stopMotors()
    {
        motorA.stop();
        motorB.stop();
    }

    private static void resetMotors()
    {
        runMotors(15);
        waitMs(2500);
        stopMotors();

        motorA.resetTachoCount();
        motorB.resetTachoCount();
    }


    static void waitMs(long time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch (Exception ex)
        {
        }
    }

}
