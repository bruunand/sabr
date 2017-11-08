package com.ballthrower.movement;

import lejos.nxt.NXTMotor;

import java.util.ArrayList;

/**
 * Created by theis on 11/6/17.
 */
public class MotorController
{
    private ArrayList<NXTMotor> _motors = new ArrayList<>();

    public MotorController(NXTMotor motor1)
    {
        _motors.add(motor1);
    }

    public MotorController(NXTMotor motor1, NXTMotor motor2)
    {
        _motors.add(motor1);
        _motors.add(motor2);
    }

    /**
     * Turns a number of degrees
     * @param degrees number of degrees to turn
     */
    protected void waitWhileTurning(int degrees)
    {
        while( Math.abs(_motors.get(0).getTachoCount()) < degrees){}
    }

    /**
     * Starts motors with a specific power and direction.
     * @param power sets the power of the motors.
     * @param direction direction of motors is forward if value is true and backwards otherwise.
     */
    protected void startMotors(int power, boolean direction)
    {
        for (NXTMotor motor : _motors)
        {
            motor.setPower(power);

            // start motors.
            if (direction)
            {
                motor.forward();
            }
            else
            {
                motor.backward();
            }
        }
    }

    /**
     * Stops Motors
     */
    protected void stopMotors()
    {
        for (NXTMotor motor: _motors)
        {
            motor.stop();
        }
    }

    /**
     * Waits an amount of miliseconds
     * @param time number of miliseconds to wait
     */
    protected void waitMiliseconds(int time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch (Exception e)
        {
            //TODO: Error handling
        }
    }

    /**
     * Reset tachocounter for motors
     */
    protected void resetTacho()
    {
        for (NXTMotor motor: _motors)
        {
            motor.resetTachoCount();
        }
    }
}
