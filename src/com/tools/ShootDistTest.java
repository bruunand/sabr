package com.tools;

import java.lang.Math;

import com.ballthrower.exceptions.OutOfRangeException;
import com.ballthrower.movement.shooting.Shooter;
import lejos.nxt.*;
import lejos.robotics.RegulatedMotor;

/**
 * Created by Thomas Buhl on 17/10/2017.
 */

public class ShootDistTest
{
    private static int distance = 100;
    private static Shooter shooter;


    private static void main(String[] options)
    {
        shooter = new Shooter(new MotorPort[] {MotorPort.A, MotorPort.B});

        Button.RIGHT.addButtonListener(new ButtonListener()
        {
            public void buttonPressed(Button b)
            {
                if (distance <= 130)
                {
                    distance += 5;
                }
                else
                {
                    distance = 135;
                };
            }

            public void buttonReleased(Button b)
            {
            }
        });

        Button.LEFT.addButtonListener(new ButtonListener()
        {
            public void buttonPressed(Button b)
            {
                if (distance >= 95)
                {
                    distance -= 5;
                }
                else
                {
                    distance = 90;
                }
            }

            public void buttonReleased(Button b)
            {
            }
        });

        Button.ENTER.addButtonListener(new ButtonListener()
        {
            public void buttonPressed(Button b)
            {
            }

            public void buttonReleased(Button b)
            {
                try {
                    shoot();
                }
                catch (OutOfRangeException e)
                {

                }
            }
        });

        try
        {
            while (!Button.ESCAPE.isDown())
            {
                LCD.drawString("Distance: " + distance + "%", 0, 2);

                Thread.sleep(50);
            }
        }
        catch (Exception ex)
        {
        }
    }



    private static void shoot() throws OutOfRangeException
    {
        shooter.shootDistance(distance);
    }
}
