package org.ojothepojo.lifx.message;

import lombok.ToString;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@ToString
public abstract class Message {
    protected static final int HEADER_LENGTH = 8 + 16 + 12; // 36 bytes header

    // FRAME
    private int size;
    private boolean tagged;
    private long source; // This is the multicast group

    // FRAME ADDRESS
    private long target; // This is a MAC address
    private boolean ackRequired;
    private boolean resRequired;
    private short sequence;

    // PROTOCOL HEADER
    private int type;

    //2900 0054 0000 0000 D073 D513 0F6E 0000 4C49 4658 5632 0000 3CB3 C542 2D16 7E14 0300 0000    017C DD00 00
    //0000 0034 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0200 0000    0000
    public void parseHeader(ByteBuffer buffer) {
        buffer.rewind();
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        this.setSize(buffer.getShort() & 0xffff);
        buffer.position(32);
        this.setType(buffer.getShort() & 0xffff);
    }

    public ByteBuffer headerToBytes() {
        return ByteBuffer
                .allocate(HEADER_LENGTH)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putShort(getSize())
                .putShort(getTagged())
                .putInt(getSource())
                .putLong(getTarget())
                .putInt(0).putShort((short)0) // 6 bytes reserved
                .put(getAckResRequired())
                .put(getSequence())
                .putLong(0L) // 8 bytes reserved
                .putShort(getType())
                .putShort((short)0);
    }

    public abstract ByteBuffer payloadToBytes();

    // GETTERS AND SETTERS

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

    // PRIVATE METHODS

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