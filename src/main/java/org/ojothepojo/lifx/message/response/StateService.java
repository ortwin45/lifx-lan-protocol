package org.ojothepojo.lifx.message.response;

import org.ojothepojo.lifx.message.ResponseMessage;

import java.nio.ByteBuffer;


public class StateService extends ResponseMessage {

    @Override
    protected int getPayloadLength() {
        return 0;
    }

    @Override
    public void parsePayload(ByteBuffer bytes) {
        //TODO
    }
}
