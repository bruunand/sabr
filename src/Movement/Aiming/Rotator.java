package Movement.Aiming;

import lejos.nxt.*;

public class Rotator implements IRotator
{
	private static NXTRegulatedMotor _motor;
	public static NXTRegulatedMotor getMotor()
	{
		return _motor;
	}

	public Rotator(NXTRegulatedMotor motor)//Input some motor
	{
		_motor = motor;
	}

	/* Ratio between gears on turning module is
	 * 56 : 40. Degrees requested must be scaled
	 * by 1.4 and floored. */
	@Override
	public void turnDegrees(int degrees)
	{
		int actualDegrees = (int)(degrees * 1.4);
		getMotor().rotate(actualDegrees);
	}

	/* TODO: Get pixel distance from center of img to object, rotate pixels -> degrees */
}