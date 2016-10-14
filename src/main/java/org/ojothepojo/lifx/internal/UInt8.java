package org.ojothepojo.lifx.internal;

import java.nio.ByteBuffer;

public class UInt8 extends FieldType {

    private short value;

    public UInt8(Number value) {
        if (value.shortValue() > 255 || value.shortValue() < 0) {
            throw new IllegalArgumentException("Value should be between [0, 255]");
        }
        this.value = value.shortValue();
    }


    @Override
    ByteBuffer toBytes() {
        return ByteBuffer
                .allocate(1)
                .put((byte) (value & 0xFF));
    }

    @Override
    String toHexString() {
        return String.format("%02X", value);
    }

    @Override
    String toBinaryString() {
        return String.format("%8s", Integer.toBinaryString(value)).replace(' ', '0');
    }
}
