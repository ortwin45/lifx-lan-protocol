package org.ojothepojo.lifx.internal;


import java.nio.ByteBuffer;

public abstract class FieldType {

    abstract ByteBuffer toBytes();

    abstract String toHexString();

    abstract String toBinaryString();
}
