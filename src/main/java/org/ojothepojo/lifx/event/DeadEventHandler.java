package org.ojothepojo.lifx.event;


import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeadEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeadEventHandler.class);

    @Subscribe
    public void handleEvent(DeadEvent event) {
        LOGGER.warn("No event handler subscribed to MessageReceivedEvents.");
    }
}
