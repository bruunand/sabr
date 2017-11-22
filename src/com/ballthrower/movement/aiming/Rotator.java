package com.ballthrower.movement.aiming;

import com.ballthrower.movement.MotorController;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;

public class Rotator extends MotorController implements IRotator
{
	/* getGearRatio = robot.numberOfGearTeeth / motor.numberOfGearTeeth. */
	private static final int MOTOR_POWER = 40;

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

		super.resetTacho();

		super.startMotors(MOTOR_POWER, degrees > 0);
		super.waitWhileTurning(actualDegrees);
		super.stopMotors();

		currentHeading += actualDegrees;
	}

	public void resetHeading()
	{
		turnDegrees(-currentHeading);
	}
}
