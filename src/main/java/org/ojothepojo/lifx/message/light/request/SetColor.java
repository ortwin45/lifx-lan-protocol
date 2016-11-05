package org.ojothepojo.lifx.message.light.request;


import lombok.Getter;
import org.ojothepojo.lifx.message.Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SetColor extends Message{

    private int hue;
    private int saturation;
    private int brightness;
    private int kelvin;
    private long duration;

    public SetColor() {
        setTagged(true);
        setSource("192.168.1.255");
        setTarget("00:00:00:00:00:00");

        setType(102);
        //setSize();
    }

    public SetColor(String targetMacAddress, String sourceIpAddress, int hue, int saturation, int brightness, int kelvin, int duration ) {
        setTagged(false);
        setSource(sourceIpAddress);
        setTarget(targetMacAddress);

        setType(102);
        //setSize(38);

        checkUnsigned16bit(hue);
        checkUnsigned16bit(saturation);
        checkUnsigned16bit(brightness);
        checkUnsigned16bit(kelvin);
        checkUnsigned32bit(duration);
        this.hue  = hue;
        this.saturation = saturation;
        this.brightness = brightness;
        this.kelvin = kelvin;
        this.duration = duration;
    }

    public SetColor(byte[] bytes) {
        super(bytes);
    }

    @Override
    protected ByteBuffer payloadToBytes() {
        ByteBuffer buf = ByteBuffer.allocate(13).order(ByteOrder.LITTLE_ENDIAN);

        buf.put((byte)0); // reserved
        buf.putShort((short)(hue & 0xffff));
        buf.putShort((short)(saturation & 0xffff));
        buf.putShort((short)(brightness & 0xffff));
        buf.putShort((short)(kelvin & 0xffff));
        buf.putInt((int)(duration & 0xffffffff));
        return buf;
    }

    @Override
    public void parsePayload(ByteBuffer buf) {
        buf.rewind();
        buf = buf.order(ByteOrder.LITTLE_ENDIAN);
        // todo
    }

    @Override
    public String toString() {
        return "SetPower" + super.toString() + "--payload(hue=" + hue + ", saturation=" + saturation+ ", brightness=" + brightness+ ", kelvin=" + kelvin+ ", duration=" + duration + ")";
    }
}
