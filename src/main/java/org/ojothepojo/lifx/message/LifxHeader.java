package org.ojothepojo.lifx.message;

import org.ojothepojo.lifx.field.Type;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 64 + 32 bits (96 bits)
 */
public class LifxHeader {

    private byte[] input;

    private Type type;

    public LifxHeader(byte[] input) {
        this.input = input;
        ByteBuffer bb = ByteBuffer.wrap(input);
        bb.order( ByteOrder.LITTLE_ENDIAN);

        short signedShort = bb.getShort(8);
        int i = signedShort >= 0 ? signedShort : 0x10000 + signedShort;
        type = Type.valueOf(i);
        bb.clear();
    }

    public byte[] getInput() {
        return input;
    }

    public Type getType() {
        return type;
    }
}
