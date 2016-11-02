package org.ojothepojo.lifx;

import org.junit.Test;
import org.ojothepojo.lifx.message.request.GetService;
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

        client.sendMessage(new GetService());
        Thread.sleep(200);

        LOGGER.debug("STOPPING");
    }

}