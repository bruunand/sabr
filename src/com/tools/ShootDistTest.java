package com.tools;

import com.ballthrower.exceptions.OutOfRangeException;
import com.ballthrower.movement.shooting.Shooter;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;
import lejos.nxt.MotorPort;

public class ShootDistTest
{
    private static int distance = 100;
    private static Shooter shooter;


    public static void main(String[] options)
    {
        shooter = new Shooter(new MotorPort[] {MotorPort.A, MotorPort.B});

        Button.RIGHT.addButtonListener(new ButtonListener()
        {
            public void buttonPressed(Button b)
            {
                if (distance <= 160)
                {
                    distance += 5;
                }
                else
                {
                    distance = 165;
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
                if (distance >= 90)
                {
                    distance -= 5;
                }
                else
                {
                    distance = 85;
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
                    LCD.drawString("OoR!", 0, 5);
                }
            }
        });

        try
        {
            while (!Button.ESCAPE.isDown())
            {
                LCD.drawString("Distance: " + distance + " cm", 0, 0);
                LCD.drawString("Power: " + shooter.rawPower, 0, 1);
                LCD.drawString("Comp. Power: " + shooter.compPower, 0, 2);
                LCD.drawString("Comp. Factor: " + shooter.compFactor, 0, 3);

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
