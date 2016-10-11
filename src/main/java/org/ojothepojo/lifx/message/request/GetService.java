package org.ojothepojo.lifx.message.request;


import org.ojothepojo.lifx.message.RequestMessage;

import java.nio.ByteBuffer;


public class GetService extends RequestMessage {

    @Override
    protected int getPayloadLength() {
        return 0;
    }

    @Override
    public ByteBuffer getPayloadBytes() {
        return ByteBuffer.allocate(0);
    }
}
