import java.lang.Math;
import lejos.nxt.*;
import lejos.robotics.RegulatedMotor;

public class ShooterTest2
{
    static NXTMotor motorA2 = new NXTMotor(MotorPort.A);
    static NXTMotor motorB2 = new NXTMotor(MotorPort.B);

    static RegulatedMotor[] motors = {new NXTRegulatedMotor(MotorPort.A), new NXTRegulatedMotor(MotorPort.B)};


    private static final byte Gears = 3;
    private static final int[] gearSizes = {40, 24};

    private static int power = 100;
    private static int realPower = 100;
    private static int maxSpeed = 800;

    private static void main(String[] options)
    {
        //maxSpeed = Math.max(maxA, maxB);

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
                    power = 20;
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
                if (power >= 25)
                {
                    power -= 5;
                }
                else
                {
                    power = 100;
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
                shoot();
            }
        });

        try
        {
            while (!Button.ESCAPE.isDown())
            {
                LCD.clear();
                /*
                LCD.drawString("Distance: " + distanc, 0, 0);
                LCD.drawString("Vel: " + getInitialVelocity(distanc) + "m/s^2", 0, 1);
                LCD.drawString("Pow: " + getPower(getInitialVelocity(distanc)) + "%", 0, 2);
                LCD.drawString("Deg: " + (int)(360 / getGearFactor()) , 0, 3);
                LCD.drawString("Gear: " + getGearFactor() , 0, 4);
               // LCD.drawString("Gear2: " + gearSizes[0]/gearSizes[1], 0, 5);
                //LCD.drawString("Gear: " + getGearFactor() , 0, 6);
                //LCD.drawString("Gear: " + getGearFactor() , 0, 7);
                */

                LCD.drawString("MaxSpeed: " + maxSpeed,  0, 1);
                LCD.drawString("Power: " + power + "%", 0, 2);
                LCD.drawString("realPower: " + realPower + "%", 0, 3);


                //LCD.drawString("Deg: " + degrees , 0, 3);
                Thread.sleep(50);
            }
        }
        catch (Exception ex)
        {
        }
    }



    private static void shoot()
    {
        int degrees = ((int)((540) / getGearFactor()));

        RegulatedMotor regMotor = new NXTRegulatedMotor(MotorPort.A);

        float speedPotential = regMotor.getMaxSpeed();
        realPower = (int)(power*(maxSpeed / speedPotential));


        motorA2.setPower(power);
        motorB2.setPower(power);

        motorA2.backward();
        motorB2.backward();

        while (Math.abs(motorA2.getTachoCount()) < degrees ){waitMs(10);}

        motorA2.stop();
        motorB2.stop();

        // wait for motors to turn once
        //while( Math.abs(motorA.getTachoCount()) < degrees){}

        //stopMotors();
        waitMs(1000);

        motorA2.setPower(10);
        motorB2.setPower(10);

        motorA2.backward();
        motorB2.backward();

        waitMs(2500);

        motorA2.stop();
        motorB2.stop();

        //resetMotors();
        motorA2.resetTachoCount();
        motorB2.resetTachoCount();

    //resetMotors();
}
    private static double getGearFactor()
    {
        return Math.pow((double)gearSizes[0]/gearSizes[1], Gears);
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
