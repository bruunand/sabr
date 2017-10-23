package Tools;

import lejos.nxt.*;
import java.io.File;
import java.util.Random;

public class RPMTest
{
    static int power = 10;

    static NXTMotor motorA = new NXTMotor(MotorPort.A);
    static NXTMotor motorB = new NXTMotor(MotorPort.B);
    
    static long lastTime = 0;
    
    private static void setPowerAndResetCounters()
    {
        lastTime = System.currentTimeMillis();
        motorA.resetTachoCount();
        motorA.setPower(power);
    }
    
    private static void main(String[] options)
    {
        motorA.forward();
        setPowerAndResetCounters();

        Button.RIGHT.addButtonListener(new ButtonListener()
        {
            public void buttonPressed(Button b)
            {
                if (power <= 90)
                    power += 10;
                setPowerAndResetCounters();
            }

            public void buttonReleased(Button b)
            {
            }
        });

        Button.LEFT.addButtonListener(new ButtonListener()
        {
            public void buttonPressed(Button b)
            {
                if (power >= 10)
                    power -= 10;
                setPowerAndResetCounters();
            }

            public void buttonReleased(Button b)
            {
            }
        });
        
        try
        {
            while (!Button.ESCAPE.isDown())
            {
                float minutesPassed = (System.currentTimeMillis() - lastTime) / 60000F;
                float rpmA = motorA.getTachoCount() / 360F / minutesPassed;
                float rpmB = motorB.getTachoCount() / 360F / minutesPassed;
                LCD.drawString("A: " + power + ", " + rpmA, 0, 0);
                LCD.drawString("B: " + power + ", " + rpmB, 0, 5);
                Thread.sleep(50);
            }
        }
        catch (Exception ex)
        {
        }
    }
    
}
