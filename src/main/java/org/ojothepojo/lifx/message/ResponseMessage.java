package org.ojothepojo.lifx.message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class ResponseMessage extends Message {



    //2900 0054 0000 0000 D073 D513 0F6E 0000 4C49 4658 5632 0000 3CB3 C542 2D16 7E14 0300 0000    017C DD00 00
    //0000 0034 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0200 0000    0000
    protected void parseHeader(ByteBuffer buffer) {
        buffer.rewind();
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.setSize(buffer.getShort() & 0xffff);
        buffer.position(32);
        this.setType(buffer.getShort() & 0xffff);
    }

    public abstract void parsePayload(ByteBuffer bytes);
}
