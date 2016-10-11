package org.ojothepojo.lifx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class NetwerkListener {

    private static Logger LOGGER = LoggerFactory.getLogger(NetwerkListener.class);

    public static final int BROADCAST_PORT = 56700;
    public static final InetSocketAddress BROADCAST_ADDRESS = new InetSocketAddress(BROADCAST_PORT);

    public void startListening() throws IOException {
        LOGGER.debug("Start listening...");

        DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(BROADCAST_ADDRESS);
        channel.socket().setBroadcast(true);
        channel.configureBlocking(true);


        while (true) {
            ByteBuffer buf = ByteBuffer.allocate(512);
            buf.clear();
            SocketAddress receive = channel.receive(buf);

            LOGGER.debug("");
        }
    }
}
