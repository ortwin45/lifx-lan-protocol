package org.ojothepojo.lifx;

import org.junit.Test;
import org.ojothepojo.lifx.message.request.GetService;
import org.ojothepojo.lifx.message.request.SetPower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientTest.class);

    @Test
    public void start() throws Exception {
        Client client = new Client();

        client.startListenerThread();
        GetService getService = new GetService();

        client.sendMessage(getService);

        Thread.sleep(1000);

        client.sendMessage(getService);
        Thread.sleep(500);

        SetPower setPower = new SetPower();
        setPower.setLevel(false);
        client.sendMessage(setPower);

        Thread.sleep(500);
        LOGGER.debug("STOPPING");

    }

}