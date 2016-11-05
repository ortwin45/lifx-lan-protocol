package org.ojothepojo.lifx.message.response;

import lombok.Getter;
import org.ojothepojo.lifx.message.Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class StatePower extends Message {

    @Getter
    private boolean level;

    @Getter
    private long port;

    public StatePower(byte[] bytes) {
        super(bytes);
    }

    @Override
    public void parsePayload(ByteBuffer buf) {
        buf.rewind();
        buf = buf.order(ByteOrder.LITTLE_ENDIAN);
        short power = buf.getShort();
        if (power == 0) {
            level = false;
        } else {
            level = true;
        }
    }

    @Override
    protected ByteBuffer payloadToBytes() {
        throw new RuntimeException("Shouldn't serialize a response message");
    }

    @Override
    public String toString() {
        return "StatePower" + super.toString() + "--payload(level=" + level + ")";
    }
}
