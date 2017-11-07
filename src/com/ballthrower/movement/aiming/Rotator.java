package com.ballthrower.movement.aiming;

import com.ballthrower.movement.MotorController;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;

public class Rotator extends MotorController implements IRotator
{
	/* GearRatio = robot.numberOfGearTeeth / motor.numberOfGearTeeth. */
	private final float GearRatio = 2.33f;

	private final int MotorPower = 40;

	/* Updates every time we turn so we can reset position. */
	private int currentHeading = 0;

	//private NXTMotor _motor;

	public Rotator(MotorPort motor)
	{
		super(new NXTMotor(motor));
	}

	/**
	 * Rotate a specific number of degrees
	 * @param degrees number of degrees to rotate
	 */
	public void turnDegrees(float degrees)
	{
		int actualDegrees = (int)(degrees * GearRatio);

		super.resetTacho();

		super.startMotors(MotorPower, degrees > 0);
		super.waitWhileTurning(actualDegrees);
		super.stopMotors();

		currentHeading += actualDegrees;
	}

	public void resetHeading()
	{
		turnDegrees(-currentHeading);
	}
}
