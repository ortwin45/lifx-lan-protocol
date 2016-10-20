package org.ojothepojo.lifx.event;


import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeadEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeadEventListener.class);


    @Subscribe
    public void handleEvent(DeadEvent event) {
        LOGGER.debug(event.toString());
    }
}
