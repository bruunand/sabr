package com.ballthrower.listeners;

import com.ballthrower.Robot;
import com.ballthrower.abortion.AbortCode;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;

public class ExitButtonListener implements ButtonListener
{
    @Override
    public void buttonPressed(Button button)
    {
    }

    @Override
    public void buttonReleased(Button button)
    {
        Robot.getInstance().abort(AbortCode.MANUAL);
    }
}
