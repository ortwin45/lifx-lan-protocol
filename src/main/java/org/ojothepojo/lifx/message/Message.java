package org.ojothepojo.lifx.message;

import com.google.common.base.Strings;
import org.ojothepojo.lifx.util.Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * This is the base class for all messages sent to and received from the bulbs.
 */
public abstract class Message {
    private static final int HEADER_LENGTH = 8 + 16 + 12; // 36 bytes header

    // FRAME
    private short size;
    private boolean tagged;
    private byte[] source; // This is just an id of the client, but in our case, it's the ip.

    // FRAME ADDRESS
    private byte[] target; // This is a MAC address
    private boolean ackRequired;
    private boolean resRequired;
    private short sequence;

    // PROTOCOL HEADER
    private short type;

    public Message(short size, short type, String target) {
        this.size = size;
        this.type = type;
        this.source = ipStringToByteArray(Util.getIpAddress());
        this.target = macAddressStringToByteArray(target);
        this.tagged = "00:00:00:00:00:00".equalsIgnoreCase(target);
    }

    protected Message(byte[] bytes) {
        parseHeader(ByteBuffer.wrap(Arrays.copyOfRange(bytes, 0, 36)));
        parsePayload(ByteBuffer.wrap(Arrays.copyOfRange(bytes, 36, bytes.length)));
    }

    // GETTERS AND SETTERS

    public void setTagged(short value) {
        tagged = ((value >> 13) & 1) == 1;
    }

    public short getTagged() {
        if (tagged) {
            return (short) 0x3400;
        } else {
            return (short) 0x1400;
        }
    }

    public String getSource() {
        return byteArrayToIpString(source);
    }

    private byte[] ipStringToByteArray(String ip) {
        Util.checkIpAddress(ip);
        String[] ipAddressInArray = ip.split("\\.");
        byte[] result = new byte[4];
        for (int i = 0; i < ipAddressInArray.length; i++) {
            result[i] = (byte) (Short.parseShort(ipAddressInArray[i]) & 0xff);
        }
        return result;
    }

    private String byteArrayToIpString(byte[] ip) {
        String result = "";
        for (byte b : ip) {
            result = result + (short) (b & 0xff) + ".";
        }
        return result.substring(0, result.length() - 1);
    }

    private byte[] macAddressStringToByteArray(String macAddress) {
        byte[] result = new byte[6];
        Util.checkMacAddress(macAddress);
        String[] split = macAddress.split(":");
        for (int i = 0; i < split.length; i++) {
            result[i] = (byte) (Integer.parseInt(split[i],16) & 0xff);
        }
        return result;
    }

    public String getTargetAsString() {
        String macAddress = "";
        for (short s : target) {
            macAddress = macAddress + Strings.padStart(Integer.toHexString(s & 0xff).toUpperCase(), 2, '0') + ":";
        }
        return macAddress.substring(0, macAddress.length() - 1);
    }


    private byte getAckResRequired() {
        int result = 0;
        if (ackRequired) {
            result = result + 2;
        }
        if (resRequired) {
            result = result + 1;
        }
        return (byte) result;
    }

    public void setSequence(short value) {
        Util.checkUnsigned8bit(value);
        sequence = value;
    }

    public byte getSequence() {
        return (byte) (sequence & 0xFF);
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
                .put(source[3]).put(source[2]).put(source[1]).put(source[0]) // little endian order
                .put(target).put((byte) 0).put((byte) 0)
                .putInt(0).putShort((short) 0) // 6 bytes reserved
                .put(getAckResRequired())
                .put(getSequence())
                .putLong(0L) // 8 bytes reserved
                .putShort(type)
                .putShort((short) 0);
    }


    protected abstract ByteBuffer payloadToBytes();


    // Deserializing

    //2900 0054 05D8 2EE2 D073 D513 0F6E 0000 4C49 4658 5632 0000 3CB3 C542 2D16 7E14 0300 0000    017C DD00 00
    //0000 0034 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0200 0000    0000
    private void parseHeader(ByteBuffer buffer) {
        buffer.rewind();
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        size = buffer.getShort();
        this.setTagged((short) (buffer.getShort() & 0xffff));

        this.source = new byte[4];
        this.source[3] = buffer.get(); // The invers order because little endian
        this.source[2] = buffer.get();
        this.source[1] = buffer.get();
        this.source[0] = buffer.get();
        byte[] target = new byte[6];
        for (int i = 0; i < target.length; i++) {
            target[i] = buffer.get();
        }
        this.target = target;


        buffer.position(32);
        type = buffer.getShort();
    }

    public abstract void parsePayload(ByteBuffer bytes);


    @Override
    public String toString() {
        return "--header(size=" + size + ", tagged=" + tagged + ", source=" + byteArrayToIpString(source) + ", target=" + getTargetAsString() + ", type=" + type + ")";
    }

}
