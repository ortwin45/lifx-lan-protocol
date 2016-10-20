package org.ojothepojo.lifx;

import com.google.common.eventbus.EventBus;
import org.ojothepojo.lifx.event.PacketReceivedEvent;
import org.ojothepojo.lifx.event.PacketReceivedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {
    private static Logger LOGGER = LoggerFactory.getLogger(Client.class);

    private EventBus eventBus;

    public Client() {
        this.eventBus = new EventBus();
        eventBus.register(new PacketReceivedListener());
    }

    public void start() {
        eventBus.post(new PacketReceivedEvent());
        MyThread myThread = new MyThread(eventBus);
        myThread.run();
    }



    public class MyThread implements Runnable {
        private EventBus eventBus;

        public MyThread(EventBus eventBus) {
            this.eventBus = eventBus;
        }

        public void run() {
            eventBus.post(new PacketReceivedEvent("Hello from the thread!"));
        }
    }

}
