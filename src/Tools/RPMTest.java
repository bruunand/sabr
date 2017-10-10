import lejos.nxt.*;
import java.io.File;
import java.util.Random;

public class RPMTest
{
    static int power = 10;
    
    static NXTMotor motor = new NXTMotor(MotorPort.A);
    
    static long lastTime = 0;
    
    private static void setPowerAndResetCounters()
    {
        lastTime = System.currentTimeMillis();
        motor.resetTachoCount();
        motor.setPower(power);
    }
    
    private static void main(String[] options)
    {
        motor.forward();
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
                float rpm = motor.getTachoCount() / 360F / minutesPassed;
                LCD.drawString("(" + power + "), (" + rpm + " RPM)", 0, 0);
                Thread.sleep(50);
            }
        }
        catch (Exception ex)
        {
        }
    }
    
}
