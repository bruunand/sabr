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
    private static final int GRAVITY = 980;
    private static final int DEPARTURE_ANGLE = 51;
    private static final float FACTOR = 9.7095f;
    private static final int OFFSET = -415;
    private RegulatedMotor regMotor;

    //private static final byte Gears = 3;
    private final boolean Direction = false;
    //private static final int[] gearSizes = {40, 24};
    private final float GearFactor = 4.630f;

    public Shooter(MotorPort[] motors)
    {
        super(new NXTMotor(motors[0]), new NXTMotor(motors[1]));
        regMotor = new NXTRegulatedMotor(motors[0]);
    }

    /**
     * Calculate the power needed to shoot a specific distance, defined by battery power. Assume linear relation between distance and motor power required.
     * @param distance the distance to shoot.
     * @return the motor power needed.
     */
    private int getPowerLinear(float distance)
    {
        double compensationFactor = 800 / regMotor.getMaxSpeed();
        return (int)(((distance * 429.7)/6.668) * compensationFactor);
    }

    /**
     *
     * @return
     */
    /*private double getGearFactor()
    {
        return Math.pow(gearSizes[0]/gearSizes[1], Gears);
    }*/

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

        int degrees = (int)((1.5*360) / GearFactor);

        super.startMotors(power, Direction);
        super.waitWhileTurning(degrees);
        super.stopMotors();

        super.waitMiliseconds(1000);

        resetMotors();
    }

    /**
     * Run motor at a slow speed to stop at tension stick
     */
    private void resetMotors()
    {
        super.startMotors(15, Direction);
        super.waitMiliseconds(2500);
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
