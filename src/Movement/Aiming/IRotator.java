package Movement.Aiming;

import Target;

/**
 * Created by Anders Brams on 10/9/2017.
 * Should be used as defined in the UML diagram
 * describing the design of the control module component.
 */
public interface IRotator {
    void TurnTowards(Target target);
}