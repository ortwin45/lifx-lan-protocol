package org.ojothepojo.lifx.message.device.response;

import lombok.Getter;
import org.ojothepojo.lifx.message.Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class StateService extends Message {

    @Getter
    private short service;

    @Getter
    private long port;

    public StateService(byte[] bytes) {
        super(bytes);
    }

    @Override
    public void parsePayload(ByteBuffer buf) {
        buf.rewind();
        buf = buf.order(ByteOrder.LITTLE_ENDIAN);
        service = (short) (buf.get() & 0xff);
        port = (long) (buf.getInt() & 0xffffffff);
    }

    @Override
    protected ByteBuffer payloadToBytes() {
        throw new RuntimeException("Shouldn't serialize a response message");
    }

    @Override
    public String toString() {
        return "StateService" + super.toString() + "--payload(service=" + service + ", port=" + port +")";
    }
}
