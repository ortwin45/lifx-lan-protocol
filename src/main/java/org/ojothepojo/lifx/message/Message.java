package org.ojothepojo.lifx.message;

import java.nio.ByteBuffer;

public abstract class Message {
    protected static final int HEADER_LENGTH = 8 + 16 + 12; // 36 bytes header

    // FRAME
    private int size;
    private boolean tagged;
    private long source;

    // FRAME ADDRESS
    private long target;
    private boolean ackRequired;
    private boolean resRequired;
    private short sequence;

    // PROTOCOL HEADER
    private int type;

    public void setSize(int value){
        checkUnsigned16bit(value);
        size = value;
    }

    public short getSize() {
        return (short) (size & 0xFFFF);
    }

    public void setTagged(boolean value) {
        tagged = value;
    }

    public short getTagged() {
        if (tagged) {
            return (short) 0x3400;
        } else {
            return (short) 0x1400;
        }
    }

    public void setSource(long value){
        checkUnsigned32bit(value);
        source = value;
    }

    public int getSource() {
        return (int) (source & 0xFFFFFFFF);
    }

    public long getTarget() {
        return target;
    }

    public byte getAckResRequired() {
        int result = 0;
        if (ackRequired) {
            result = result + 2;
        }
        if (resRequired) {
            result = result + 1;
        }
        return (byte)result;
    }

    public void setSequence(short value) {
        checkUnsigned8bit(value);
        sequence = value;
    }

    public byte getSequence() {
        return (byte) (sequence & 0xFF);
    }

    public void setType(int value) {
        checkUnsigned16bit(value);
        type = value;
    }

    public short getType() {
        return (short) (type & 0xFFFF);
    }

    private void checkUnsigned8bit(int value) {
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException("Value must be an unsigned 8-bit integer");
        }
    }

    private void checkUnsigned16bit(int value) {
        if (value < 0 || value > 65535) {
            throw new IllegalArgumentException("Value must be an unsigned 16-bit integer");
        }
    }

    private void checkUnsigned32bit(long value) {
        if (value < 0 || value > 4294967295L) {
            throw new IllegalArgumentException("Value must be an unsigned 32-bit integer");
        }
    }
}
