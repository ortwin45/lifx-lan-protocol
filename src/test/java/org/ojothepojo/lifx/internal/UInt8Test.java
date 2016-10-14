package org.ojothepojo.lifx.internal;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.assertj.core.api.Assertions.assertThat;


public class UInt8Test {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor() {
        new UInt8(256);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor2() {
        new UInt8(-1);
    }

    @Test
    public void testToBytes() {
        UInt8 value = new UInt8(255);
        ByteBuffer byteBuffer = value.toBytes();
        byteBuffer.rewind();
        byte b = byteBuffer.get();
        assertThat(b).isEqualTo((byte) -1);
    }

    @Test
    public void testToHexString() {
        UInt8 value = new UInt8(255);
        assertThat(value.toHexString()).isEqualTo("FF");
        value = new UInt8(0);
        assertThat(value.toHexString()).isEqualTo("00");
        value = new UInt8(127);
        assertThat(value.toHexString()).isEqualTo("7F");
    }

    @Test
    public void testToBinaryString() {
        UInt8 value = new UInt8(255);
        assertThat(value.toBinaryString()).isEqualTo("11111111");
        value = new UInt8(0);
        assertThat(value.toBinaryString()).isEqualTo("00000000");
        value = new UInt8(127);
        assertThat(value.toBinaryString()).isEqualTo("01111111");
        value = new UInt8(128);
        assertThat(value.toBinaryString()).isEqualTo("10000000");
    }
}