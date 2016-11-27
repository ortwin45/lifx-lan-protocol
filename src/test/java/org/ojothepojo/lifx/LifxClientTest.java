package org.ojothepojo.lifx;

import org.junit.Test;
import org.ojothepojo.lifx.event.LoggingEventHandler;
import org.ojothepojo.lifx.message.light.request.GetColor;
import org.ojothepojo.lifx.message.light.request.SetColor;
import org.ojothepojo.lifx.util.Util;

import java.io.IOException;
import java.util.Random;

public class LifxClientTest {

    /**
     * You won't see anything incoming, but you can still send outgoing messages
     */
    @Test
    public void testWithoutListener() throws IOException, InterruptedException {
        LifxClient client = new LifxClient();
        client.sendMessage(new SetColor("D0:73:D5:13:00:9B", Util.getIpAddress(), new Random().nextInt(65535), 3000, 65535, 0, 0));
        client.stop();
    }

    /**
     * You can now receive incoming messages as well if you start the listener thread
     */
    @Test
    public void testWithPacketListener() throws IOException, InterruptedException {
        LifxClient client = new LifxClient();
        client.startListenerThread();
        client.sendMessage(new GetColor("D0:73:D5:13:00:9B", Util.getIpAddress()));
        Thread.sleep(500); // wait a bit for the bulb to respond.
        client.stop();
    }

    @Test
    public void testWithLoggingEventHandler() throws InterruptedException, IOException {
        LifxClient client = new LifxClient();
        client.startListenerThread();
        client.getEventBus().register(new LoggingEventHandler());
        client.sendMessage(new GetColor("D0:73:D5:13:00:9B", Util.getIpAddress()));
        Thread.sleep(500); // wait a bit for the bulb to respond.
        client.stop();
    }

}