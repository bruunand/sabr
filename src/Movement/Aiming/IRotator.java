package Movement.Aiming;

/**
 * Created by Anders Brams on 10/9/2017.
 * Should be used as defined in the UML diagram
 * describing the design of the control module component.
 */
public interface IRotator 
{
    void turn(int pixels);
    void resetHeading();
}