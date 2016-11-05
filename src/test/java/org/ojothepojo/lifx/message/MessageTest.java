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
    }

    @Test
    public void doodle2() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        String hostAddress = localHost.getHostAddress();
    }

    @Test
    public void testIp() {
        Message message = new TestMessage();
        String ip = "192.168.1.255";
        message.setSource(ip);
        assertThat(message.getSourceAsString()).isEqualTo(ip);
    }

    @Test
    public void testTarget() {
        Message message = new TestMessage();
        String macAddress = "D0:FF:D5:13:00:9B";
        message.setTarget(macAddress);
        assertThat(message.getTargetAsString()).isEqualTo(macAddress);
    }

    private class TestMessage extends Message {

        @Override
        protected ByteBuffer payloadToBytes() {
            return null;
        }

        @Override
        public void parsePayload(ByteBuffer bytes) {

        }
    }
}