package org.ojothepojo.lifx.message;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;


public class MessageTest {

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
        String macAddress = "D0:73:D5:13:00:9B";
        message.setTarget(macAddress);
        assertThat(message.getTargetAsString()).isEqualTo(macAddress);
    }

    private class TestMessage extends Message {

    }
}