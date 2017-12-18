package com.tools;

import com.ballthrower.exceptions.OutOfRangeException;
import com.ballthrower.movement.shooting.Shooter;
import lejos.nxt.*;

public class PowerTest
{
    private static int power = 100;
    private static Shooter shooter;


    public static void main(String[] options)
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
                LCD.drawString("Comp. Power: " + shooter.compPower + " %", 0, 1);
                LCD.drawString("C. Factor: " + shooter.compFactor + " %", 0, 2);

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
