package com.ballthrower.movement.aiming;

import com.ballthrower.movement.MotorController;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.Sound;
import lejos.nxt.LCD;

public class Rotator extends MotorController implements IRotator
{
    /* getGearRatio = robot.numberOfGearTeeth / motor.numberOfGearTeeth. */
    private static final int MOTOR_POWER = 20;

    /* Updates every time we turn so we can reset position. */
    private int currentHeading = 0;

    //private NXTMotor _motor;

    public Rotator(MotorPort motor)
    {
        super(new NXTMotor(motor), 2.33f);
    }

    /**
     * Rotate a specific number of degrees
     * @param degrees number of degrees to rotate
     */
    public void turnDegrees(float degrees)
    {
        int actualDegrees = (int)(degrees * getGearRatio());

        LCD.drawString("Turn: " + degrees, 0, 1);
        LCD.drawString("Maffs:" + actualDegrees, 0, 2);

        super.resetTacho();

        Sound.beep();
        super.startMotors(MOTOR_POWER, degrees > 0);
        super.waitWhileTurning(actualDegrees);
        Sound.buzz();
        super.stopMotors();

        currentHeading += actualDegrees;
    }

    public void resetHeading()
    {
        turnDegrees(-currentHeading);
    }
}
