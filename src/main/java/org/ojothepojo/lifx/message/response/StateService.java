package org.ojothepojo.lifx.message.response;

import lombok.Getter;
import lombok.ToString;
import org.ojothepojo.lifx.message.ResponseMessage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class StateService extends ResponseMessage {

    @Getter
    private short service;
    @Getter
    private long port;

    public StateService(byte[] bytes) {
        parseHeader(ByteBuffer.wrap(Arrays.copyOfRange(bytes, 0, 36)));
        parsePayload(ByteBuffer.wrap(Arrays.copyOfRange(bytes, 36, bytes.length)));
    }

    @Override
    public void parsePayload(ByteBuffer buf) {
        buf.rewind();
        buf = buf.order(ByteOrder.LITTLE_ENDIAN);
        service = (short) (buf.get() & 0xff);
        port = (long) (buf.getInt() & 0xffffffff);
    }

    @Override
    public String toString() {
        return super.toString() + "(service=" + service + ", port=" + port +")";
    }
}
