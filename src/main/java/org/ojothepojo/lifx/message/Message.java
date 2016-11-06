package org.ojothepojo.lifx.message;

import com.google.common.base.Strings;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public abstract class Message {
    protected static final int HEADER_LENGTH = 8 + 16 + 12; // 36 bytes header

    // FRAME
    private short size;
    private boolean tagged;
    private long source; // This is the multicast group or the ip of the client.

    // FRAME ADDRESS
    private short[] target; // This is a MAC address
    private boolean ackRequired;
    private boolean resRequired;
    private short sequence;

    // PROTOCOL HEADER
    private int type;

    public Message(short size) {
        this.size = size;
    }

    public Message(byte[] bytes) {
        parseHeader(ByteBuffer.wrap(Arrays.copyOfRange(bytes, 0, 36)));
        parsePayload(ByteBuffer.wrap(Arrays.copyOfRange(bytes, 36, bytes.length)));
    }

    // GETTERS AND SETTERS

    public void setTagged(boolean value) {
        tagged = value;
    }

    public void setTagged(short value) {
        tagged = ((value >> 13) & 1) == 1 ? true : false;
    }

    public boolean getTaggedAsBoolean() {
        return tagged;
    }

    public short getTagged() {
        if (tagged) {
            return (short) 0x3400;
        } else {
            return (short) 0x1400;
        }
    }

    public void setSource(String ipAddress){
        checkIpAddress(ipAddress);
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
        checkMacAddress(macAddress);
        target = new short[6];
        String[] split = macAddress.split(":");
        for (int i = 0; i < split.length; i++) {
            target[i] = Short.parseShort(split[i], 16);
        }
    }

    protected void setTarget(short[] shorts) {
        target = shorts;
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
        for (short s : target) {
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

    protected void setType(int value) {
        checkUnsigned16bit(value);
        type = value;
    }

    public short getType() {
        return (short) (type & 0xFFFF);
    }


    // Serializing

    public ByteBuffer toBytes() {
        ByteBuffer header = headerToBytes();
        header.rewind();
        ByteBuffer payload = payloadToBytes();
        payload.rewind();
        return ByteBuffer.allocate(header.capacity() + payload.capacity())
                .put(header)
                .put(payload);
    }

    private ByteBuffer headerToBytes() {
        return ByteBuffer
                .allocate(HEADER_LENGTH)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putShort(size)
                .putShort(getTagged())
                .putInt(getSource())
                .put(getTarget()).put((byte)0).put((byte)0)
                .putInt(0).putShort((short)0) // 6 bytes reserved
                .put(getAckResRequired())
                .put(getSequence())
                .putLong(0L) // 8 bytes reserved
                .putShort(getType())
                .putShort((short)0);
    }


    protected abstract ByteBuffer payloadToBytes();



    // Deserializing

    //2900 0054 05D8 2EE2 D073 D513 0F6E 0000 4C49 4658 5632 0000 3CB3 C542 2D16 7E14 0300 0000    017C DD00 00
    //0000 0034 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0200 0000    0000
    protected void parseHeader(ByteBuffer buffer) {
        buffer.rewind();
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        size = buffer.getShort();
        this.setTagged((short)(buffer.getShort() & 0xffff));

        //buffer.position(4);
        this.setSource(buffer.getInt() & 0xffffffff);

        short[] target = new short[6];
        for (int i = 0; i < target.length; i++ ) {
            target[i] = buffer.get();
        }
        this.setTarget(target);

        buffer.position(32);
        this.setType(buffer.getShort() & 0xffff);
    }

    public abstract void parsePayload(ByteBuffer bytes);



    @Override
    public String toString() {
        return "--header(size=" + size + ", tagged="+ getTaggedAsBoolean() + ", source="+ getSourceAsString() + ", target="+ getTargetAsString() + ", type="+ getType() +")";
    }

    // PRIVATE METHODS

    private void checkUnsigned8bit(int value) {
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException("Value must be an unsigned 8-bit integer");
        }
    }

    protected void checkUnsigned16bit(int value) {
        if (value < 0 || value > 65535) {
            throw new IllegalArgumentException("Value must be an unsigned 16-bit integer");
        }
    }

    protected void checkUnsigned32bit(long value) {
        if (value < 0 || value > 4294967295L) {
            throw new IllegalArgumentException("Value must be an unsigned 32-bit integer");
        }
    }

    private void checkMacAddress(String macAddress) {
        if (!macAddress.matches("^([0-9A-Fa-f]{2}[:]){5}([0-9A-Fa-f]{2})$")) {
            throw new IllegalArgumentException("Mac Address must be in the form 00:00:00:00:00:00");
        }
    }

    private void checkIpAddress(String ipAddress) {
        if (!ipAddress.matches("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")) {
            throw new IllegalArgumentException("Mac Address must be in the form 255.255.255.255");
        }
    }

    private long ipToLong(String ipAddress) {
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

    private String longToIp(long ip) {
        return ((ip >> 24) & 0xFF) + "."
                + ((ip >> 16) & 0xFF) + "."
                + ((ip >> 8) & 0xFF) + "."
                + (ip & 0xFF);
    }
}
