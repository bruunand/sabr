package com.sabr.movement.shooting;

import com.sabr.exceptions.OutOfRangeException;

/*
 * Should be used as defined in the UML diagram
 * describing the design of the control module component.
 */
public interface IShooter
{
    void shootDistance(float distance)throws OutOfRangeException;
}
