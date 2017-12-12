package com.tools;

import com.ballthrower.exceptions.OutOfRangeException;
import com.ballthrower.movement.shooting.Shooter;
import lejos.nxt.*;

/**
 * Created by Thomas Buhl on 17/10/2017.
 */

public class PowerTest
{
    private static int power = 100;
    private static Shooter shooter;


    private static void main(String[] options)
    {
        shooter = new Shooter(new MotorPort[] {MotorPort.A, MotorPort.B});

        Button.RIGHT.addButtonListener(new ButtonListener()
        {
            public void buttonPressed(Button b)
            {
                if (power <= 95)
                {
                    power += 5;
                }
                else
                {
                    power = 100;
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
                if (power >= 55)
                {
                    power -= 5;
                }
                else
                {
                    power = 50;
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
                LCD.drawString("Power: " + power + " %", 0, 0);
                LCD.drawString("Comp. Power: " + shooter.CompensatedPower + " %", 0, 1);
                LCD.drawString("C. Factor: " + shooter.CompensationFactor + " %", 0, 2);

                Thread.sleep(50);
            }
        }
        catch (Exception ex)
        {
        }
    }



    private static void shoot() throws OutOfRangeException
    {
        shooter.shootAtPower(power);
    }
}
