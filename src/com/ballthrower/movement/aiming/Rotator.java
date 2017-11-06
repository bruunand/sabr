package com.ballthrower.movement.aiming;

import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;

public class Rotator implements IRotator
{
	/* GearRatio = robot.numberOfGearTeeth / motor.numberOfGearTeeth. */
	private final float GearRatio = 2.33f;

	/* Updates every time we turn so we can reset position. */
	private int currentHeading = 0;

	private NXTRegulatedMotor _motor;

	public Rotator(MotorPort motor)
	{
		_motor = new NXTRegulatedMotor(motor);
	    _motor.setSpeed(50);
	}

	/* Ratio between gears on turning module is
	 * 56 : 40. Degrees requested must be scaled
	 * by 1.4 and floored to i. */
	@Override
	public void turnDegrees(float degrees)
	{
		int actualDegrees = (int)(degrees * GearRatio);
		_motor.rotate(actualDegrees);
		currentHeading += actualDegrees;
	}

	public void resetHeading()
	{
		turnDegrees(-currentHeading);
	}
}
