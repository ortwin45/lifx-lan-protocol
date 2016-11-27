package org.ojothepojo.lifx;

import org.junit.Test;
import org.ojothepojo.lifx.message.device.request.GetPower;
import org.ojothepojo.lifx.message.device.request.GetService;
import org.ojothepojo.lifx.message.device.request.SetPower;
import org.ojothepojo.lifx.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientTest.class);

    @Test
    public void start() throws Exception {
        LifxClient lifxClient = new LifxClient();
        String ipAddress = Util.getIpAddress();

        lifxClient.startListenerThread();
        GetService getService = new GetService();
        lifxClient.sendMessage(getService);
        LOGGER.debug("");
        Thread.sleep(1500);
        LOGGER.debug("");

        GetPower getPower = new GetPower("D0:73:D5:13:00:9B", ipAddress);
        lifxClient.sendMessage(getPower);
        LOGGER.debug("");
        Thread.sleep(500);

        SetPower setPower = new SetPower("D0:73:D5:13:00:9B", ipAddress, false);
        lifxClient.sendMessage(setPower);
        LOGGER.debug("");
        Thread.sleep(500);

        setPower = new SetPower("D0:73:D5:13:00:9B", ipAddress, true);
        lifxClient.sendMessage(setPower);
        LOGGER.debug("");
        Thread.sleep(500);

        LOGGER.debug("STOPPING");

    }
}