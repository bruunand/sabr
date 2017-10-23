package Movement;

import Movement.Aiming.IRotator;
import Movement.Aiming.Rotator;
import lejos.nxt.NXTRegulatedMotor;

public class MovementController
{
    private IRotator _rotator;
    public IRotator getRotator()
    {
        return _rotator;
    }

    public MovementController(NXTRegulatedMotor rotate, NXTRegulatedMotor shooter)
    {
        _rotator = new Rotator(rotate);
        /* Init shooter */
    }
}
