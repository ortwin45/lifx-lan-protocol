package org.ojothepojo.lifx.event;


import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketReceivedListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(PacketReceivedListener.class);


    @Subscribe
    public void handleIncomingPacket(PacketReceivedEvent event) {
        LOGGER.info("I got the event! " + event.getMessage());

    }
}
