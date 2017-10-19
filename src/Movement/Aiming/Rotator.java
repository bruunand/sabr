package Movement.Aiming;

import lejos.nxt.*;

public class Rotator implements IRotator
{
	/* gearRatio = from.numberOfGearTeeth / to.numberOfGearTeeth */
	private final float gearRatio = 1.4f;

	/* arctan(y-distance (cm) from camera to object / x-distance (cm) from camera to object )
	*                                       divided by
	*                        number of pixels from center of img to object                 */
	private final float pixelDegreeRatio = 0.1017f;

	/* updates every time we turn so we can reset position */
	private int currentHeading = 0;

	private static NXTRegulatedMotor _motor;
	public static NXTRegulatedMotor getMotor()
	{
		return _motor;
	}

	public Rotator(NXTRegulatedMotor motor)//Input some motor
	{
		_motor = motor;
	}

    @Override
    public void turn(int pixels)
    {
        float degrees = convertPixelsToDegrees(pixels);
        turnDegrees(degrees);
    }

	/* Ratio between gears on turning module is
	 * 56 : 40. Degrees requested must be scaled
	 * by 1.4 and floored to i. */
	private void turnDegrees(float degrees)
	{
		int actualDegrees = (int)(degrees * gearRatio);
		getMotor().rotate(actualDegrees);
		currentHeading += actualDegrees;
	}

	public void resetHeading()
	{
		turnDegrees(-currentHeading);
	}

	private float convertPixelsToDegrees(int pixels)
	{
		return pixels * pixelDegreeRatio;
	}
}