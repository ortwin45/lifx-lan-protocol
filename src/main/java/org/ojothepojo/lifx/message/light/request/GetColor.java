package org.ojothepojo.lifx.message.light.request;


import org.ojothepojo.lifx.message.Message;

import java.nio.ByteBuffer;

public class GetColor extends Message{

    public GetColor() {
        setTagged(true);
        setSource("192.168.1.255");
        setTarget("00:00:00:00:00:00");
        setType(101);
        setSize(36);
    }

    public GetColor(String targetMacAddress, String sourceIpAddress) {
        setTagged(false);
        setSource(sourceIpAddress);
        setTarget(targetMacAddress);
        setType(101);
        setSize(36);
    }

    public GetColor(byte[] bytes) {
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
        return "GetColor" + super.toString() + "--payload()";
    }
}