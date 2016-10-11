package org.ojothepojo.lifx.message;


import java.nio.ByteBuffer;

public abstract class RequestMessage extends Message {

    protected abstract ByteBuffer getPayloadBytes();

    protected ByteBuffer getBytes() {
        ByteBuffer headerBytes = getHeaderBytes();
        headerBytes.rewind();

        ByteBuffer payloadBytes = getPayloadBytes();
        payloadBytes.rewind();

        return ByteBuffer.allocate(getLength()).put(headerBytes).put(payloadBytes);
    }
}
