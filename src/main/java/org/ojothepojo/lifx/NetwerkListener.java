package org.ojothepojo.lifx;

import org.ojothepojo.lifx.message.LifxMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.Collections;

/**
 */
public class NetwerkListener {

    private static Logger LOGGER = LoggerFactory.getLogger(NetwerkListener.class);

    public static final int BROADCAST_PORT = 56700;

    public void startListening() throws IOException {
        LOGGER.debug("Start listening...");

        DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(BROADCAST_PORT));
        channel.socket().setBroadcast(true);
        channel.configureBlocking(true);

        while (true) {
            ByteBuffer buf = ByteBuffer.allocate(512);
            buf.clear();
            SocketAddress receive = channel.receive(buf);
            LOGGER.debug("Reveived on " + receive.toString() + " " + Arrays.toString(buf.array()));
            LifxMessage message = new LifxMessage(buf.array());
            LOGGER.debug("");
        }
    }


}
