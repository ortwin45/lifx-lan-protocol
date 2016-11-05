package org.ojothepojo.lifx;

import org.junit.Test;
import org.ojothepojo.lifx.message.request.GetPower;
import org.ojothepojo.lifx.message.request.GetService;
import org.ojothepojo.lifx.message.request.SetPower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientTest.class);

    @Test
    public void start() throws Exception {
        Client client = new Client();
        String ipAddress = getIpAddress();

        client.startListenerThread();
        GetService getService = new GetService();
        client.sendMessage(getService);
        LOGGER.debug("");
        Thread.sleep(500);
        LOGGER.debug("");

        GetPower getPower = new GetPower("D0:73:D5:13:00:9B", ipAddress);
        client.sendMessage(getPower);
        LOGGER.debug("");
        Thread.sleep(500);

        SetPower setPower = new SetPower("D0:73:D5:13:00:9B", ipAddress, false);
        client.sendMessage(setPower);
        LOGGER.debug("");
        Thread.sleep(500);

        setPower = new SetPower("D0:73:D5:13:00:9B", ipAddress, true);
        client.sendMessage(setPower);
        LOGGER.debug("");
        Thread.sleep(500);

        LOGGER.debug("STOPPING");

    }

    private String getIpAddress() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

}