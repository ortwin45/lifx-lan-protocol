package org.ojothepojo.lifx;

import org.junit.Test;
import org.ojothepojo.lifx.message.light.request.GetColor;
import org.ojothepojo.lifx.message.light.request.SetColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientColorTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientColorTest.class);

    @Test
    public void start() throws Exception {
        Client client = new Client();
        String ipAddress = getIpAddress();

        client.startListenerThread();
        GetColor getColor = new GetColor("D0:73:D5:13:00:9B", ipAddress);
        client.sendMessage(getColor);
        LOGGER.debug("");
        Thread.sleep(500);

        SetColor setColor = new SetColor("D0:73:D5:13:00:9B", ipAddress, 60000, 65000, 30000, 0, 200);
        client.sendMessage(setColor);
        LOGGER.debug("");
        Thread.sleep(500);

        setColor = new SetColor("D0:73:D5:13:00:9B", ipAddress, 20000, 65000, 60000, 0, 200);
        client.sendMessage(setColor);
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