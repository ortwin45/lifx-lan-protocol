package org.ojothepojo.lifx.message.device.request;


import org.ojothepojo.lifx.message.Message;

import java.nio.ByteBuffer;

public class GetPower extends Message{

    public GetPower() {
        super((short)36);
        setTagged(true);
        setSource("192.168.1.255");
        setTarget("00:00:00:00:00:00");
        setType(20);
    }

    public GetPower(String targetMacAddress, String sourceIpAddress) {
        super((short) 36);
        setTagged(false);
        setSource(sourceIpAddress);
        setTarget(targetMacAddress);
        setType(20);
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
