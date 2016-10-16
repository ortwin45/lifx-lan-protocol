package org.ojothepojo.lifx.message;

import org.junit.Test;

import java.nio.ByteBuffer;

public class RequestMessageTest {

    @Test
    public void headerBytes() throws Exception {
        RequestMessage message = new TestMessage();
        message.setSize(Message.HEADER_LENGTH);
        message.setTagged(true);
        message.setType(2);
        ByteBuffer byteBuffer = message.headerToBytes();
    }




    class TestMessage extends RequestMessage {
        @Override
        public ByteBuffer payloadToBytes() {
            return null;
        }
    }
}