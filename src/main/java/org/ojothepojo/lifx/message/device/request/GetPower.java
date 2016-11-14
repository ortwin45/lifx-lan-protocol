package org.ojothepojo.lifx.message.device.request;


import org.ojothepojo.lifx.message.Message;

import java.nio.ByteBuffer;

public class GetPower extends Message{

    public GetPower() {
        super((short)36, (short)20, "192.168.1.255", "00:00:00:00:00:00");
        setTagged(true);
    }

    public GetPower(String targetMacAddress, String sourceIpAddress) {
        super((short) 36, (short)20, sourceIpAddress, targetMacAddress);
        setTagged(false);
    }

    public GetPower(byte[] bytes) {
        super(bytes);
    }

    @Override
    public ByteBuffer payloadToBytes() {
        return ByteBuffer.allocate(0);
    }

    @Override
    public void parsePayload(ByteBuffer bytes) {
        // No payload for this message
    }

    @Override
    public String toString() {
        return "GetPower" + super.toString() + "--payload()";
    }
}
