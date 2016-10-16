package org.ojothepojo.lifx.message.request;


import org.ojothepojo.lifx.message.RequestMessage;

import java.nio.ByteBuffer;

public class GetService extends RequestMessage {

    public GetService() {
        setTagged(true);
        setType(02);
        setSource(3794720773L);
    }

    @Override
    public ByteBuffer payloadToBytes() {
        return null;
    }
}
