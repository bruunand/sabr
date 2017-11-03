package com.ballthrower.movement;

import com.ballthrower.movement.aiming.IRotator;
import com.ballthrower.movement.aiming.Rotator;
import com.ballthrower.movement.shooting.IShooter;
import com.ballthrower.movement.shooting.Shooter;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;

public class MovementController
{
    private IRotator _rotator;
    public IRotator getRotator()
    {
        return _rotator;
    }

    private IShooter _shooter;
    public IShooter getShooter() { return _shooter; }


    public MovementController(MotorPort rotator, MotorPort[] shooters)
    {
        /* Init rotator */
        _rotator = new Rotator(rotator);
        /* Init shooter */
        _shooter = new Shooter(shooters);
    }

    public void Shoot(float distance)
    {
        _shooter.Shoot(distance);
    }

    public void Turn(float degrees)
    {
        _rotator.turnDegrees(degrees);
    }

    public void resetHeading()
    {
        _rotator.resetHeading();
    }
}
