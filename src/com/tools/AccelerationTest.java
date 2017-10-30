import lejos.nxt.*;
import java.io.File;
import java.util.Random;

public class AccelerationTest
{
    static int power = 50;
    
    static NXTMotor motor = new NXTMotor(MotorPort.A);
    
    static long lastResetTime = 0;
    static double lastAverage = 0;

    // Finals
    static final byte numRounds = 5;
    static final short minDegrees = 160;

    private static void resetCounters()
    {
        lastResetTime = System.currentTimeMillis();
        motor.setPower(power);
        motor.resetTachoCount();
    }
    
    private static void main(String[] options)
    {
        Button.RIGHT.addButtonListener(new ButtonListener()
        {
            public void buttonPressed(Button b)
            {
                if (power <= 90)
                    power += 10;
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
                long sum = 0;
                for (int i = 0; i < numRounds; i++)
                {
                    resetCounters();
                    motor.forward();
                    while (motor.getTachoCount() < minDegrees);
                    sum += (System.currentTimeMillis() - lastResetTime);
                    motor.stop();
                    waitMs(250);
                }

                lastAverage = sum / (numRounds * 1F);
            }
        });

        while (!Button.ESCAPE.isDown())
        {
            LCD.drawString("Power: " + power + "%", 0, 0);
            LCD.drawString("Time: " + lastAverage + " ms", 0, 5);
            waitMs(50);
        }
    }

    static void waitMs(long time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch (Exception ex)
        {
        }
    }
    
}
