package org.ojothepojo.lifx.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class RequestMessage extends Message {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestMessage.class);

    public ByteBuffer toBytes() {
        ByteBuffer header = headerToBytes();
        header.rewind();
        ByteBuffer payload = payloadToBytes();
        payload.rewind();
        ByteBuffer result = ByteBuffer.allocate(header.capacity() + payload.capacity())
                .put(header)
                .put(payload);
        return result;
    }

    private ByteBuffer headerToBytes() {
        ByteBuffer result = ByteBuffer
                .allocate(HEADER_LENGTH)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putShort(getSize())
                .putShort(getTagged())
                .putInt(getSource())
                .putLong(getTarget())
                .putInt(0).putShort((short)0) // 6 bytes reserved
                .put(getAckResRequired())
                .put(getSequence())
                .putLong(0L) // 8 bytes reserved
                .putShort(getType())
                .putShort((short)0) // 2 bytes reserved
                ;

        return result;
    }


    protected abstract ByteBuffer payloadToBytes();


}
