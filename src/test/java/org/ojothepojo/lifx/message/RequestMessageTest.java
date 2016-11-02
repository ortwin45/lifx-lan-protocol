package org.ojothepojo.lifx.message;

import org.junit.Test;

import java.nio.ByteBuffer;

public class RequestMessageTest {

    @Test
    public void headerBytes() throws Exception {
        Message message = new TestMessage();
        message.setSize(Message.HEADER_LENGTH);
        message.setTagged(true);
        message.setType(2);
        ByteBuffer byteBuffer = message.toBytes();
    }


    class TestMessage extends Message {
        @Override
        public ByteBuffer payloadToBytes() {
            return null;
        }

        @Override
        public void parsePayload(ByteBuffer bytes) {
            // Do nothing
        }
    }
}