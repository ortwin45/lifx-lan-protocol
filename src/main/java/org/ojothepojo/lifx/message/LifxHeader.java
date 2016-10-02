package org.ojothepojo.lifx.message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 64 + 32 bits (96 bits)
 */
public class LifxHeader {

    private int type;

    public LifxHeader(byte[] input) {
        ByteBuffer bb = ByteBuffer.wrap(input);
        bb.order( ByteOrder.LITTLE_ENDIAN);

        short signedShort = bb.getShort(8);
        type = signedShort >= 0 ? signedShort : 0x10000 + signedShort;
        bb.clear();

    }


}
