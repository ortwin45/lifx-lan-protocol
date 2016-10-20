package org.ojothepojo.lifx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OtherClient {
    private static Logger LOGGER = LoggerFactory.getLogger(OtherClient.class);

    public void doWork() {
        MyThread myThread = new MyThread();
        new Thread(myThread).start();
    }


    class MyThread implements Runnable {

        @Override
        public void run() {
            LOGGER.debug("Starting mythread");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.debug("Ending myThread");
        }
    }
}
