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
        assertThat(b).isEqualTo((short)255);

        byte s1 = (byte)0xFF;
        assertThat(s1).isEqualTo(((byte) -1));

        byte b1 = (byte)255;
        assertThat(b1).isEqualTo((byte)-1);
        assertThat(b1 & 0xFF).isEqualTo(255);
    }

    @Test
    public void doodle2() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        String hostAddress = localHost.getHostAddress();
    }

    @Test
    public void testIp() throws UnknownHostException {
        Message message = new TestMessage((short)0, (short)0);
        String ip = "192.168.1.255";
        message.setSource(ip);
        assertThat(message.getSourceAsString()).isEqualTo(ip);

        byte[] address = new byte[4];
        address[0] = (byte)192;
        address[1] = (byte)168;
        address[2] = (byte)1;
        address[3] = (byte)255;
        InetAddress byAddress = InetAddress.getByAddress(address);
        assertThat(byAddress.getHostAddress()).isEqualTo("192.168.1.255");

    }

    @Test
    public void testTarget() {
        Message message = new TestMessage((short)0, (short)0);
        String macAddress = "D0:FF:D5:13:00:9B";
        message.setTarget(macAddress);
        assertThat(message.getTargetAsString()).isEqualTo(macAddress);
    }

    @Test
    public void testTagged() {
        Message message = new TestMessage((short)0,(short)0);
        message.setTagged(true);
        short tagged = message.getTagged();
        assertThat((tagged >> 13) & 1).isEqualTo(1);

        message.setTagged((short) 0x3400);
        assertThat(message.getTaggedAsBoolean()).isTrue();
        message.setTagged((short) 0x1400);
        assertThat(message.getTaggedAsBoolean()).isFalse();
        message.setTagged((short) 0x5400);
        assertThat(message.getTaggedAsBoolean()).isFalse();
    }

    private class TestMessage extends Message {

        public TestMessage(short size, short type) {
            super(size, type);
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