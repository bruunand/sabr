package com.ballthrower.movement.aiming;

import com.ballthrower.movement.MotorController;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.NXTRegulatedMotor;

public class Rotator extends MotorController implements IRotator
{
    private NXTRegulatedMotor _regMotor;

    /* Updates every time we turn so we can reset position. */
    private int currentHeading = 0;

    public Rotator(MotorPort motor)
    {
        super(new NXTMotor(motor), 5.625f);
        _regMotor = new NXTRegulatedMotor(MotorPort.C);
        _regMotor.setSpeed(_regMotor.getMaxSpeed());
    }

    /**
     * Rotate a specific number of degrees
     * @param degrees number of degrees to rotate
     */
    public void turnDegrees(float degrees)
    {
        int actualDegrees = (int) (degrees * getGearRatio());

        _regMotor.rotate(-actualDegrees);

        currentHeading += actualDegrees;
    }

    public void resetHeading()
    {
        turnDegrees(-currentHeading);
    }
}
