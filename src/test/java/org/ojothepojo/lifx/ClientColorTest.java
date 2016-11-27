package org.ojothepojo.lifx;

import org.junit.Test;
import org.ojothepojo.lifx.message.light.request.GetColor;
import org.ojothepojo.lifx.message.light.request.SetColor;
import org.ojothepojo.lifx.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientColorTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientColorTest.class);

    @Test
    public void start() throws Exception {
        LifxClient lifxClient = new LifxClient();
        String ipAddress = Util.getIpAddress();

        lifxClient.startListenerThread();
        GetColor getColor = new GetColor("D0:73:D5:13:00:9B", ipAddress);
        lifxClient.sendMessage(getColor);
        LOGGER.debug("");
        Thread.sleep(500);

        SetColor setColor = new SetColor("D0:73:D5:13:00:9B", ipAddress, 60000, 65000, 30000, 0, 200);
        lifxClient.sendMessage(setColor);
        LOGGER.debug("");
        Thread.sleep(500);

        setColor = new SetColor("D0:73:D5:13:00:9B", ipAddress, 20000, 100, 60000, 0, 200);
        lifxClient.sendMessage(setColor);
        LOGGER.debug("");
        Thread.sleep(500);

        LOGGER.debug("STOPPING");

    }
}
