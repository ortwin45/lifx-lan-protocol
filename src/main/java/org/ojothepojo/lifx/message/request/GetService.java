package org.ojothepojo.lifx.message.request;


import org.ojothepojo.lifx.message.Message;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class GetService extends Message {

    public GetService() {
        setTagged(true);
        setType(02);
        setSource("192.168.1.255");
        setTarget("0:0:0:0:0:0");
    }

    public GetService(byte[] bytes) {
        parseHeader(ByteBuffer.wrap(Arrays.copyOfRange(bytes, 0, 36)));
        parsePayload(ByteBuffer.wrap(Arrays.copyOfRange(bytes, 36, bytes.length)));
    }

    @Override
    public ByteBuffer payloadToBytes() {
        return ByteBuffer.allocate(0);
    }

    @Override
    public void parsePayload(ByteBuffer bytes) {
        // No payload for this message
    }
}
