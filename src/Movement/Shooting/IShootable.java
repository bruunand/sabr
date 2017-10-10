package Movement.Shooting;

import Movement.Aiming.Target;

/**
 * Created by Anders Brams on 10/10/2017.
 * Should be used as defined in UML diagram
 * describing design of the control module component.
 */
public interface IShootable
{
    void ShootAt(Target target);
}
