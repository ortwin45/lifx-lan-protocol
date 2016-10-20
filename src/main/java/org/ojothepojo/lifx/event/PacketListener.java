package org.ojothepojo.lifx.event;

import com.google.common.eventbus.EventBus;
import org.ojothepojo.lifx.message.Message;
import org.ojothepojo.lifx.message.response.StateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class PacketListener implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(PacketListener.class);

    private EventBus eventBus;

    private DatagramSocket socket;

    public PacketListener(EventBus eventBus, DatagramSocket socket) {
        this.eventBus = eventBus;
        this.socket = socket;

    }

    @Override
    public void run() {
        try {
            while (true) {
                byte[] buf = new byte[512];
                DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
                socket.receive(receivePacket);
                LOGGER.debug(receivePacket.getAddress() +  " " + DatatypeConverter.printHexBinary(buf));
                Message message = new StateService();
                eventBus.post(new MessageReceivedEvent(message));
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
