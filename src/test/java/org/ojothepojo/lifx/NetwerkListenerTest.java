package org.ojothepojo.lifx;

import org.junit.Test;

import static org.junit.Assert.*;

public class NetwerkListenerTest {
    private NetwerkListener listener = new NetwerkListener();

    @Test
    public void startListening() throws Exception {
        listener.startListening();
    }

}