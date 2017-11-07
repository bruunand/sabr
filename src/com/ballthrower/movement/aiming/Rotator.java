package com.ballthrower.movement.aiming;

import com.ballthrower.movement.MotorController;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;

public class Rotator extends MotorController implements IRotator
{
	/* GearRatio = robot.numberOfGearTeeth / motor.numberOfGearTeeth. */
	private final float GearRatio = 2.33f;

	/* Updates every time we turn so we can reset position. */
	private int currentHeading = 0;

	//private NXTMotor _motor;

	public Rotator(MotorPort motor)
	{
		super(new NXTMotor(motor));
	    //_motor.setPower(50);
	}

	/* Ratio between gears on turning module is
	 * 56 : 40. Degrees requested must be scaled
	 * by 1.4 and floored to i. */
	@Override
	public void turnDegrees(float degrees)
	{
		int actualDegrees = (int)(degrees * GearRatio);

		super.resetTacho();
		
		super.startMotors(30, degrees > 0);
		super.turnDegrees(actualDegrees);
		super.stopMotors();

		currentHeading += actualDegrees;
	}

	public void resetHeading()
	{
		turnDegrees(-currentHeading);
	}
}
