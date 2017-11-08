package com.ballthrower.listeners;

import com.ballthrower.Robot;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;

public class ExitButtonListener implements ButtonListener
{
    private final Robot _instance;

    public ExitButtonListener(Robot instance)
    {
        this._instance = instance;
    }

    @Override
    public void buttonPressed(Button button)
    {
    }

    @Override
    public void buttonReleased(Button button)
    {
        this._instance.abort(Robot.AbortCode.MANUAL);
    }
}
