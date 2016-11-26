package org.ojothepojo.lifx;

import com.google.common.eventbus.EventBus;
import org.ojothepojo.lifx.event.LoggingEventHandler;
import org.ojothepojo.lifx.event.PacketListenerThread;
import org.ojothepojo.lifx.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Main class to start sending and receiving bulb messages.
 */
public class LifxClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(LifxClient.class);
    private static final int BROADCAST_PORT = 56700;

    private final EventBus eventBus;

    private final DatagramSocket socket;

    public LifxClient() throws SocketException {
        this.eventBus = new EventBus();
        eventBus.register(new LoggingEventHandler());
        this.socket = new DatagramSocket(BROADCAST_PORT);
    }

    /**
     * Creates a thread that start listening for packets send by the bulbs.
     */
    public void startListenerThread() throws InterruptedException {
        Thread listenerThread = new Thread(new PacketListenerThread(eventBus, socket));
        listenerThread.start();
        Thread.sleep(100);
    }

    public void sendMessage(Message message) throws IOException {
        LOGGER.debug("Sending message: " + message.toString());
        InetAddress address = InetAddress.getByName("192.168.1.255");
        // I should probably send messages to a single ip when possible.

        DatagramPacket sendPacket = new DatagramPacket(
                message.toBytes().array(),
                message.toBytes().array().length,
                address,
                BROADCAST_PORT);
        socket.send(sendPacket);
    }
}
