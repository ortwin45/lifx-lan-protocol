package org.ojothepojo.lifx;

import org.ojothepojo.lifx.message.Message;
import org.ojothepojo.lifx.message.request.GetService;
import org.ojothepojo.lifx.message.response.StateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class BroadCaster {
    private static Logger LOGGER = LoggerFactory.getLogger(BroadCaster.class);

    public static final int BROADCAST_PORT = 56700;
    public static final InetSocketAddress BROADCAST_ADDRESS = new InetSocketAddress(BROADCAST_PORT);


    public void doBroadcast() throws IOException, InterruptedException {
        GetService getService = new GetService();

            DatagramSocket socket = new DatagramSocket(56700);
            InetAddress address = InetAddress.getByName("192.168.1.255");
        socket.setBroadcast(true);

        DatagramPacket sendPacket = new DatagramPacket(getService.toBytes().array(),
                getService.toBytes().array().length, address, 56700);
        socket.send(sendPacket);
        socket.setBroadcast(false);
        ByteBuffer buf = ByteBuffer.allocate(512);

        int i  = 2;
        while (i > 0) {
            DatagramPacket receivePacket = new DatagramPacket(buf.array(), 512);
            socket.receive(receivePacket);
            LOGGER.debug(receivePacket.getAddress() +  " " + DatatypeConverter.printHexBinary(buf.array()));
            Message message = new StateService();
            message.parseHeader(buf);
            LOGGER.debug(message.toString());
            LOGGER.debug("");
            Thread.sleep(100);
            i--;
        }

    }
}
