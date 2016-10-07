package org.ojothepojo.lifx;

import org.junit.Test;

public class NetwerkListenerTest {
    private NetwerkListener listener = new NetwerkListener();

    @Test
    public void startListening() throws Exception {
        listener.startListening();
    }

}