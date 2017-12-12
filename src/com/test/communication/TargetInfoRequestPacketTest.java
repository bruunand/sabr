package com.test.communication;

import com.ballthrower.abortion.AbortCode;
import com.ballthrower.abortion.IAbortable;
import com.ballthrower.communication.Connection;
import com.ballthrower.communication.ConnectionFactory;
import com.ballthrower.communication.packets.TargetInfoRequestPacket;
import com.ballthrower.exceptions.AssertException;
import com.test.NXTAssert;
import com.test.Test;

import java.io.IOException;

public class TargetInfoRequestPacketTest extends Test implements IAbortable
{
    TargetInfoRequestPacket rp;

    public void setUp()
    {
        rp = new TargetInfoRequestPacket();
    }

    public void constructFromConnectionForcedFailTest() throws AssertException
    {
        NXTAssert test = new NXTAssert();
        try
        {
            Connection conn = new ConnectionFactory().createInstance(ConnectionFactory.ConnectionType.Bluetooth, this);
            rp.constructFromConnection(conn);
        }
        catch (IOException e)
        {
            /* Force success */
            test.assertThat(true, "").isTrue();
            return;
        }

        /* Force failure */
        test.assertThat(true, "").isFalse();

    }

    public void runAllTests() throws AssertException
    {
        setUp();
        constructFromConnectionForcedFailTest();
    }

    @Override
    public void abort(AbortCode code) {

    }

    @Override
    public void abort(AbortCode code, String message) {

    }

    @Override
    public void warn(String message) {

    }
}
