package org.ojothepojo.lifx.message.request;


import org.ojothepojo.lifx.message.RequestMessage;

import java.nio.ByteBuffer;

public class GetService extends RequestMessage {

    public GetService() {
        setTagged(true);
        setType(02);
        setSource("192.168.1.255");
        setTarget("0:0:0:0:0:0");
    }

    @Override
    public ByteBuffer payloadToBytes() {
        return ByteBuffer.allocate(0);
    }
}
