package org.ojothepojo.lifx.event;


import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class can be used to create a log statement for each event bulb message that is received.
 */
public class LoggingEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingEventHandler.class);

    @Subscribe
    public void handleEvent(MessageReceivedEvent event) {
        if (event.getMessage() != null) {
            LOGGER.info(event.getMessage().toString());
        }
    }
}
