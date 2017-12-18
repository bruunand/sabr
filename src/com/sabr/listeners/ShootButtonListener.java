package com.sabr.listeners;

import com.sabr.Robot;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;

public class ShootButtonListener implements ButtonListener
{
    @Override
    public void buttonPressed(Button button)
    {
    }

    @Override
    public void buttonReleased(Button button)
    {
        Robot.getInstance().locateAndShoot();
    }
}
