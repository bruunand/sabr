package com.ballthrower.movement.shooting;

import java.lang.Math;

import com.ballthrower.movement.MotorController;
import lejos.nxt.*;
import lejos.robotics.RegulatedMotor;

/**
 * Created by Thomas Buhl on 17/10/2017.
 */
public class Shooter extends MotorController implements IShooter
{
    private static final double g = 980;
    private static final int departureAngle = 45;
    private static final double factor = 5.0895;
    private static final double offset = 27.746;
    private static RegulatedMotor regMotor;

    private static final byte Gears = 3;
    private static final int[] gearSizes = {40, 24};

    public Shooter(MotorPort[] motors)
    {
        super(new NXTMotor(motors[0]), new NXTMotor(motors[1]));
        regMotor = new NXTRegulatedMotor(motors[0]);
    }

    private int getPowerLinear(float distance)
    {
        double compensationFactor = 800 / regMotor.getMaxSpeed();
        return (int)(((distance * 429.7)/6.668) * compensationFactor);
    }

    private double getGearFactor()
    {
        return Math.pow(gearSizes[0]/gearSizes[1], Gears);
    }

    public void shootDistance(float distance)
    {
        /*
        double initialVelocity = getInitialVelocity(distance);
        int power = getPower(initialVelocity);
        */
        int power = getPowerLinear(distance);

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

        super.startMotors(power, Gears % 2 == 0);
        super.turnDegrees(degrees);
        super.stopMotors();

        super.waitMiliseconds(1000);

        resetMotors();
    }

    private void resetMotors()
    {
        super.startMotors(15, Gears % 2 == 0);
        super.waitMiliseconds(2500);
        super.stopMotors();

        super.resetTacho();
    }

    private double getInitialVelocity(float distance)
    {
        // Calculate and return the required initial velocity given the target distance, gravity and departure angle.
        return Math.sqrt((distance * g)/Math.sin(2*Math.toRadians(departureAngle)));
    }

    private int getPower(double velocity)
    {
        int power = (int)(((velocity) - offset)/factor);
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
