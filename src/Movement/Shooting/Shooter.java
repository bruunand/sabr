package movement.shooting;
import java.lang.Math;
import lejos.nxt.*;

/**
 * Created by Thomas Buhl on 17/10/2017.
 */
public class Shooter
{
    private static final double g = 9.8;
    private static final int departureAngle = 45;
    private static final double factor = 1;
    private static NXTMotor motorA = new NXTMotor(MotorPort.A);
    private static NXTMotor motorB = new NXTMotor(MotorPort.B);
    private static final byte Gears = 3;
    private static final int[] gearSizes = {40, 24};

    private double getInitialVelocity(Double distance)
    {
        // Calculate and return the required initial velocity given the target distance, gravity and departure angle.
        return Math.sqrt((distance * g)/Math.sin(2*Math.toRadians(departureAngle)));
    }

    private int getPower(double velocity)
    {
        double maxDistance = 4;
        double maxVelocity = getInitialVelocity(maxDistance);

        // Calculate power as a direct linear function.
        int power = (velocity < maxVelocity)? 100 * (int)(velocity / maxVelocity): 100;

        return power;
    }

    private double getGearFactor()
    {
        return Math.pow(gearSizes[0]/gearSizes[1], Gears);
    }



    public void Shoot(Double distance)
    {
        double initialVelocity = getInitialVelocity(distance);
        int power = getPower(initialVelocity);

        // Debug.Log(This, factor, power);

        // ready motors
        motorA.setPower(power);
        motorB.setPower(power);

        int degrees = (int)(360 / getGearFactor());

        // start motors.
        if (Gears % 2 == 0)
        {
            motorA.forward();
            motorA.forward();
        }
        else
        {
            motorA.backward();
            motorA.backward();
        }
        // wait for motors to turn once
        while( Math.abs(motorA.getTachoCount()) < degrees ){}

        LCD.drawString("Power: " + power + ".", 0, 0);
        LCD.drawString("InitialVel: " + initialVelocity + ", ", 0, 5);
    }
}
