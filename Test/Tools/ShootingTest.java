package Tools;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

public class ShootingTest
{
    static int _currentPower = 50;
    static NXTRegulatedMotor _m1 = Motor.A;
    static NXTRegulatedMotor _m2 = Motor.B;

    private static void main(String[] options)
    {
        Button.RIGHT.addButtonListener(new ButtonListener()
        {
            @Override
            public void buttonPressed(Button button)
            {
                if (_currentPower < 95)
                    _currentPower += 5;
            }

            @Override
            public void buttonReleased(Button button) {}
        });

        Button.LEFT.addButtonListener(new ButtonListener()
        {
            @Override
            public void buttonPressed(Button button)
            {
                if (_currentPower > 5)
                    _currentPower -= 5;
            }

            @Override
            public void buttonReleased(Button button) {}
        });

        Button.ENTER.addButtonListener(new ButtonListener()
        {
            @Override
            public void buttonPressed(Button button)
            {
                Shoot(_currentPower);
            }

            @Override
            public void buttonReleased(Button button) {}
        });
    }

    private static void Shoot(int power)
    {
        _m1.setSpeed(power);
        _m2.setSpeed(power);
        /* Immediate return (true) allows simultaneous actuating */
        _m1.rotate(360, true);
        _m2.rotate(360, true);
    }
}
