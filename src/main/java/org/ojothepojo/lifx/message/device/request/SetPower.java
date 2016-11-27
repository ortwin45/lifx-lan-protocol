package org.ojothepojo.lifx.message.device.request;


import org.ojothepojo.lifx.message.Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SetPower extends Message{

    private short level;

    public SetPower(boolean level) {
        super((short) 38, (short) 21, "00:00:00:00:00:00");
        setLevel(false);
    }

    public SetPower(String targetMacAddress, boolean level) {
        super((short) 38, (short) 21, targetMacAddress);
        setLevel(level);
    }

    public SetPower(byte[] bytes) {
        super(bytes);
    }

    private void setLevel(boolean level) {
        if (level) {
            this.level = (short)65535;
        } else {
            this.level = (short)0;
        }
    }

    @Override
    protected ByteBuffer payloadToBytes() {
        ByteBuffer buf = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN);
        buf.putShort(level);
        return buf;
    }

    @Override
    public void parsePayload(ByteBuffer buf) {
        buf.rewind();
        buf = buf.order(ByteOrder.LITTLE_ENDIAN);
        level = (short)(buf.getShort() & 0xffff);
    }

    @Override
    public String toString() {
        return "SetPower" + super.toString() + "--payload(level=" + level + ")";
    }
}
