package org.ojothepojo.lifx.message;

import com.google.common.base.Strings;

public abstract class Message {
    protected static final int HEADER_LENGTH = 8 + 16 + 12; // 36 bytes header

    // FRAME
    private int size;
    private boolean tagged;
    private long source; // This is the multicast group

    // FRAME ADDRESS
    private byte[] target; // This is a MAC address
    private boolean ackRequired;
    private boolean resRequired;
    private short sequence;

    // PROTOCOL HEADER
    private int type;


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

    public void setSource(String ipAddress){
        source = ipToLong(ipAddress);
    }

    protected void setSource(int ipAsInt) {
        source = ipAsInt;
    }

    public int getSource() {
        return (int) (source & 0xFFFFFFFF);
    }

    public String getSourceAsString() {
        return longToIp(source);
    }

    public void setTarget(String macAddress) {
        target = new byte[6];
        String[] split = macAddress.split(":");
        for (int i = 0; i < split.length; i++) {
            target[i] = Byte.parseByte(split[i], 16);
        }
    }

    protected void setTarget(byte[] bytes) {
        target = bytes;
    }

    public byte[] getTarget() {
        byte[] result = new byte[6];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (target[i] & 0xff);
        }
        return result;
    }

    public String getTargetAsString(){
        String macAddress= "";
        for (byte s : target) {
            macAddress = macAddress + Strings.padStart(Integer.toHexString(s & 0xff).toUpperCase(), 2, '0') + ":";
        }
        return macAddress.substring(0, macAddress.length() - 1);
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

    @Override
    public String toString() {
        return "Message(size=" + getSize() + ", source="+ getSourceAsString() + ", target="+ getTargetAsString() + ", type="+ getType() +")";
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

    public long ipToLong(String ipAddress) {
        long result = 0;
        String[] ipAddressInArray = ipAddress.split("\\.");
        for (int i = 3; i >= 0; i--) {
            long ip = Long.parseLong(ipAddressInArray[3 - i]);
            //left shifting 24,16,8,0 and bitwise OR
            //1. 192 << 24
            //1. 168 << 16
            //1. 1   << 8
            //1. 2   << 0
            result |= ip << (i * 8);
        }
        return result;
    }

    public String longToIp(long ip) {
        return ((ip >> 24) & 0xFF) + "."
                + ((ip >> 16) & 0xFF) + "."
                + ((ip >> 8) & 0xFF) + "."
                + (ip & 0xFF);
    }
}
