package com.sabr.movement.aiming;

/**
 * Should be used as defined in the UML diagram
 * describing the design of the control module component.
 */
public interface IRotator 
{
    void turnDegrees(float degrees);
    void resetHeading();
}