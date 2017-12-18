package com.ballthrower.movement;

import lejos.nxt.NXTMotor;

import java.util.ArrayList;

public class MotorController
{
    private ArrayList<NXTMotor> _motors = new ArrayList<>();

    private float _gearRatio;
    public float getGearRatio()
    {
        return _gearRatio;
    }

    public MotorController(NXTMotor motor1, float gearRatio)
    {
        _motors.add(motor1);
        _gearRatio = gearRatio;
    }

    public MotorController(NXTMotor motor1, NXTMotor motor2, float gearRatio)
    {
        _motors.add(motor1);
        _motors.add(motor2);
        _gearRatio = gearRatio;
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
