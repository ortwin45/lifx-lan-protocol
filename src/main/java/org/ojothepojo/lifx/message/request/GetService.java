package org.ojothepojo.lifx.message.request;


import org.ojothepojo.lifx.message.RequestMessage;

import java.nio.ByteBuffer;

public class GetService extends RequestMessage {

    public GetService() {
        setTagged(true);
        setType(02);
    }

    @Override
    public ByteBuffer payloadBytes() {
        return null;
    }
}
