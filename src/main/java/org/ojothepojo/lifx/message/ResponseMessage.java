package org.ojothepojo.lifx.message;

import java.nio.ByteBuffer;

public abstract class ResponseMessage extends Message {

    public abstract void parsePayload(ByteBuffer bytes);
}
