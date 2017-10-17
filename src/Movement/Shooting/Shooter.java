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
        return Math.sqrt((distance * g)/Math.sin(2*Math.toRadians(departureAngle)));
    }

    private int getPower(double velocity)
    {
        float maxDistance = 4;
        double maxVelocity = getInitialVelocity(maxDistance);
        int power = 100 * (int)(velocity / maxVelocity);

        return power;
    }


    @Override
    public void Shoot(Float distance)
    {
        double power = getPower(getInitialVelocity(distance));

        // Debug.Log(This, factor, power);
    }
}
