package org.ojothepojo.lifx;


import org.ojothepojo.lifx.message.request.GetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class BroadCaster {
    private static Logger LOGGER = LoggerFactory.getLogger(BroadCaster.class);

    public static final int BROADCAST_PORT = 56700;
    public static final InetSocketAddress BROADCAST_ADDRESS = new InetSocketAddress(BROADCAST_PORT);


    public void doBroadcast() throws IOException {
        GetService getService = new GetService();
        DatagramSocket socket = new DatagramSocket(56700);
        InetAddress address = InetAddress.getByName("192.168.1.255");

        DatagramPacket packet = new DatagramPacket(getService.headerBytes().array(),
                getService.headerBytes().array().length, address, 56700);
        socket.send(packet);
        socket.close();



        DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(BROADCAST_ADDRESS);
        channel.socket().setBroadcast(true);
        //channel.configureBlocking(true);


        while (true) {
            ByteBuffer buf = ByteBuffer.allocate(512);
            buf.clear();
            SocketAddress receive = channel.receive(buf);


            LOGGER.debug(receive.toString() +  " " + DatatypeConverter.printHexBinary(buf.array()));
            LOGGER.debug("");
        }
    }
}
