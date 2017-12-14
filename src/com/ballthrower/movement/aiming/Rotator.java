package com.ballthrower.movement.aiming;

import com.ballthrower.Robot;
import com.ballthrower.movement.MotorController;
import lejos.nxt.*;
import lejos.robotics.RegulatedMotor;

public class Rotator extends MotorController implements IRotator
{
    /* getGearRatio = robot.numberOfGearTeeth / motor.numberOfGearTeeth. */
    private static final int MOTOR_POWER = 20;

    NXTRegulatedMotor _regMotor;


    /* Updates every time we turn so we can reset position. */
    private int currentHeading = 0;

    //private NXTMotor _motor;

    public Rotator(MotorPort motor)
    {
        super(new NXTMotor(motor), 5.625f);
        _regMotor = new NXTRegulatedMotor(MotorPort.C);
        _regMotor.setSpeed(500);
    }

    /**
     * Rotate a specific number of degrees
     * @param degrees number of degrees to rotate
     */
    public void turnDegrees(float degrees)
    {
        int actualDegrees = (int) (degrees * getGearRatio());

        Robot robot = Robot.getInstance();
        if (robot.isDebug())
        {
            robot.sendDebugMessage("Turn: " + degrees);
            robot.sendDebugMessage("Actual: " + actualDegrees);
        }

        _regMotor.rotate(-actualDegrees);

        currentHeading += actualDegrees;
    }

    public void resetHeading()
    {
        turnDegrees(-currentHeading);
    }
}
