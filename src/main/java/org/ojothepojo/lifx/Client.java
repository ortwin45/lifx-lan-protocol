package org.ojothepojo.lifx;

import com.google.common.eventbus.EventBus;
import org.ojothepojo.lifx.event.PacketListenerThread;
import org.ojothepojo.lifx.event.DeadEventHandler;
import org.ojothepojo.lifx.message.Message;
import org.ojothepojo.lifx.message.RequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Client {
    private static Logger LOGGER = LoggerFactory.getLogger(Client.class);
    public static final int BROADCAST_PORT = 56700;


    private EventBus eventBus;

    private DatagramSocket socket;

    public Client() throws SocketException {
        this.eventBus = new EventBus();
        eventBus.register(new DeadEventHandler());
        this.socket = new DatagramSocket(BROADCAST_PORT);
        //this.socket.setBroadcast(true); // Is this needed?
    }

    public void startListenerThread() throws InterruptedException {
        Thread listenerThread = new Thread(new PacketListenerThread(eventBus, socket));
        listenerThread.start();
        Thread.sleep(100);
    }

    public void sendMessage(RequestMessage message) throws IOException {
        LOGGER.debug("Sending message: " + message.toString());
        InetAddress address = InetAddress.getByName("192.168.1.255");

        DatagramPacket sendPacket = new DatagramPacket(
                message.toBytes().array(),
                message.toBytes().array().length,
                address,
                BROADCAST_PORT);
        socket.send(sendPacket);
    }
}
