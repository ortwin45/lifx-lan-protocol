package org.ojothepojo.lifx;

import com.google.common.eventbus.EventBus;
import lombok.Getter;
import org.ojothepojo.lifx.event.PacketListenerThread;
import org.ojothepojo.lifx.message.Message;
import org.ojothepojo.lifx.message.device.request.GetPower;
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
    private static final int PORT = 56700;
    private Thread listenerThread;
    private final DatagramSocket socket;

    @Getter
    private final EventBus eventBus;


    public LifxClient() throws SocketException {
        this.eventBus = new EventBus();
        this.socket = new DatagramSocket(PORT);
    }

    /**
     * Creates a thread that start listening for packets send by the bulbs.
     */
    public void startListenerThread() throws InterruptedException {
        listenerThread = new Thread(new PacketListenerThread(eventBus, socket));
        listenerThread.start();
    }

    public void stop() throws InterruptedException, IOException {
        if (listenerThread != null && listenerThread.isAlive()) {
            listenerThread.interrupt();
            LOGGER.info("Sending one last packet to interrupt the PacketListenerThread");
            sendMessage(new GetPower());
        }
    }

    public void sendMessage(Message message) throws IOException {
        LOGGER.debug("Sending message: " + message.toString());
        // Here, we could limit the address to a single ip...
        InetAddress address = InetAddress.getByName("192.168.1.255");

        DatagramPacket sendPacket = new DatagramPacket(
                message.toBytes().array(),
                message.toBytes().array().length,
                address,
                PORT);
        socket.send(sendPacket);
    }
}
