package Movement.Shooting;
import java.lang.Math;

/**
 * Created by Thomas Buhl on 17/10/2017.
 */
public class Shooter implements IShooter
{
    private static final double g = 9.8;
    private static final int departureAngle = 45;
    private double factor = 1;

    private double getInitialVelocity(Float distance)
    {
        // Calculate and return the required initial velocity given the target distance, gravity and departure angle.
        return Math.sqrt((distance * g)/Math.sin(2*Math.toRadians(departureAngle)));
    }

    private int getPower(double velocity)
    {
        float maxDistance = 4;
        double maxVelocity = getInitialVelocity(maxDistance);

        // Calculate power as a direct linear function.
        int power = (velocity < maxVelocity)? 100 * (int)(velocity / maxVelocity): 100;

        return power;
    }


    @Override
    public void Shoot(Float distance)
    {
        double power = getPower(getInitialVelocity(distance));

        // Debug.Log(This, factor, power);
    }
}
