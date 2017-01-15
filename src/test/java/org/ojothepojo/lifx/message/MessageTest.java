package org.ojothepojo.lifx.message;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import static org.assertj.core.api.Assertions.*;


public class MessageTest {

    @Test
    public void doodle() {
        short b = Short.parseShort("FF", 16);
        assertThat(b).isEqualTo((short) 255);

        byte s1 = (byte) 0xFF;
        assertThat(s1).isEqualTo(((byte) -1));

        byte b1 = (byte) 255;
        assertThat(b1).isEqualTo((byte) -1);
        assertThat(b1 & 0xFF).isEqualTo(255);

        short someUnsignedShort = 255;
        byte bb = (byte) (someUnsignedShort & 0xFF);
        int i = bb;
        i = bb & 0xFF;
        assertThat(bb).isEqualTo((byte) -1);

        someUnsignedShort = 256;
        bb = (byte) (someUnsignedShort & 0xFF);
        assertThat(bb).isEqualTo((byte) 0);
    }

    @Test
    public void testIp() throws UnknownHostException {
        Message message = new TestMessage((short) 0, (short) 0, "00:00:00:00:00:00");

        assertThat(message.getSource()).contains("192.168.1");

        byte[] address = new byte[4];
        address[0] = (byte) 192;
        address[1] = (byte) 168;
        address[2] = (byte) 1;
        address[3] = (byte) 255;
        InetAddress byAddress = InetAddress.getByAddress(address);
        assertThat(byAddress.getHostAddress()).isEqualTo("192.168.1.255");
    }


    @Test
    public void testTarget() {
        String macAddress = "D0:FF:D5:13:00:9B";
        Message message = new TestMessage((short) 0, (short) 0, macAddress);
        assertThat(message.getTargetAsString()).isEqualTo(macAddress);
    }

    @Test
    public void testTagged() {
        Message message = new TestMessage((short) 0, (short) 0, "00:00:00:00:00:00");
        short tagged = message.getTagged();
        assertThat((tagged >> 13) & 1).isEqualTo(1);

        message = new TestMessage((short) 0, (short) 0, "FF:FF:FF:FF:FF:FF");
        tagged = message.getTagged();
        assertThat((tagged >> 13) & 1).isEqualTo(0);
    }

    private class TestMessage extends Message {

        TestMessage(short size, short type, String macAddress) {
            super(size, type, macAddress);
        }

        @Override
        protected ByteBuffer payloadToBytes() {
            return null;
        }

        @Override
        public void parsePayload(ByteBuffer bytes) {
        }
    }
}