package com.test.movement.shooting;

import com.ballthrower.exceptions.AssertException;
import com.ballthrower.exceptions.OutOfRangeException;
import com.ballthrower.movement.shooting.Shooter;
import com.test.NXTAssert;
import com.test.Test;
import lejos.nxt.MotorPort;

public class ShooterTest extends Test
{
    private Shooter _shooter;

    private void shootDistanceOutOfRangeTest() throws AssertException
    {
        /* Targets above 1m should be ignored. */
        float distance = 900f;
        NXTAssert test = new NXTAssert();
        _shooter = new Shooter(
                new MotorPort[] {MotorPort.A, MotorPort.B});

        try
        {
            _shooter.shootDistance(distance);
        }
        catch (OutOfRangeException e)
        {
            /* Force test success. */
            test.assertThat(true, "Shooter:shootDistance")
                    .isTrue();
            return;
        }

        /* Something went wrong if we didn't return yet. */
        /* Force test failure. */
        test.assertThat(true, "Shooter:shootDistance")
                .isFalse();
    }

    public void runAllTests() throws AssertException
    {
        shootDistanceOutOfRangeTest();
    }
}
