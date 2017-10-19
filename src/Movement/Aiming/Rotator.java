package Movement.Aiming;

import lejos.nxt.*

public class Rotator implements IRotator
{
	private Motor _motor

	public Motor getMotor()
	{
		return _motor;
	}

	public Rotator(Motor motor)//Input some motor
	{
		_motor = motor;
	}

	public void rotate(float degrees)
	{
		
	}
}