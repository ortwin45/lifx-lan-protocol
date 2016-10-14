package org.ojothepojo.lifx.message;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.ojothepojo.lifx.internal.UInt16;
import org.ojothepojo.lifx.internal.UInt32;
import org.ojothepojo.lifx.internal.UInt64;
import org.ojothepojo.lifx.internal.UInt8;

import java.nio.ByteBuffer;

abstract class Message {
    private static final int HEADER_LENGTH = 64 + 128 + 96;

    // Frame fields
    @Getter
    private UInt16 size;
    @Getter
    private byte origin = 0;
    private boolean tagged;
    @Getter
    private boolean addressable = true;
    private UInt16 protocol;
    private UInt32 source;

    // Frame Address fields
    private UInt64 target;
    private byte[] reserved1;
    private byte reserved2;
    private boolean ackRequired;
    private boolean resRequired;
    private UInt8 sequence;

    // Protocol Header fields
    private UInt64 reserved3;
    private UInt16 type;
    private byte[] reserved4;

    protected abstract int getPayloadLength();

    protected int getLength() {
        return getPayloadLength() + HEADER_LENGTH;
    }

    protected ByteBuffer getHeaderBytes() {
        ByteBuffer bytes = ByteBuffer.allocate(HEADER_LENGTH);


        return bytes;
    }

}
