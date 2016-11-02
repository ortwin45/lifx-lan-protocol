package org.ojothepojo.lifx.message.request;


import org.ojothepojo.lifx.message.Message;

import java.nio.ByteBuffer;

public class GetService extends Message {

    public GetService() {
        setTagged(true);
        setType(02);
        setSource("192.168.1.255");
        setTarget("0:0:0:0:0:0");
    }

    public GetService(byte[] bytes) {
        super(bytes);
    }

    @Override
    public ByteBuffer payloadToBytes() {
        return ByteBuffer.allocate(0);
    }

    @Override
    public void parsePayload(ByteBuffer bytes) {
        // No payload for this message
    }

    @Override
    public String toString() {
        return "GetService" + super.toString()+ "--payload()";
    }
}
