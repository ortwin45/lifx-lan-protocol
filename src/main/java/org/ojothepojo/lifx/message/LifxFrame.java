package org.ojothepojo.lifx.message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 64 bits
 */
public class LifxFrame {

    private byte[] input;

    private int size;

    public LifxFrame(byte[] input) {
        this.input = input;
        ByteBuffer bb = ByteBuffer.wrap(input);
        bb.order( ByteOrder.LITTLE_ENDIAN);

        short signedShort = bb.getShort();
        size = signedShort >= 0 ? signedShort : 0x10000 + signedShort;
        bb.clear();
    }

    public byte[] getInput() {
        return input;
    }

    public int getSize() {
        return size;
    }
}
