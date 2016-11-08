package org.ojothepojo.lifx.message.light.request;

import org.ojothepojo.lifx.message.Message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SetColor extends Message {

    private short hue;
    private short saturation;
    private short brightness;
    private short kelvin;
    private int duration;

    public SetColor() {
        super((short) 49, (short) 102, "192.168.1.255");
        setTagged(true);
        setTarget("00:00:00:00:00:00");
    }

    public SetColor(String targetMacAddress, String sourceIpAddress, int hue, int saturation, int brightness, int kelvin, long duration) {
        super((short) 49, (short) 102, sourceIpAddress);
        setTagged(false);
        setTarget(targetMacAddress);

        checkUnsigned16bit(hue);
        checkUnsigned16bit(saturation);
        checkUnsigned16bit(brightness);
        checkUnsigned16bit(kelvin);
        checkUnsigned32bit(duration);
        this.hue = (short) hue;
        this.saturation = (short) saturation;
        this.brightness = (short) brightness;
        this.kelvin = (short) kelvin;
        this.duration = (int) duration;
    }

    public SetColor(byte[] bytes) {
        super(bytes);
    }

    @Override
    protected ByteBuffer payloadToBytes() {
        ByteBuffer buf = ByteBuffer.allocate(13).order(ByteOrder.LITTLE_ENDIAN);

        buf.put((byte) 0); // reserved
        buf.putShort(hue);
        buf.putShort(saturation);
        buf.putShort(brightness);
        buf.putShort(kelvin);
        buf.putInt(duration);
        return buf;
    }

    @Override
    public void parsePayload(ByteBuffer buf) {
        buf.rewind();
        buf = buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.get(); // reserved
        hue = buf.getShort();
        saturation = buf.getShort();
        brightness = buf.getShort();
        kelvin = buf.getShort();
        duration = buf.getInt();
    }

    @Override
    public String toString() {
        return "SetColor" + super.toString() + "--payload(hue=" + (hue & 0xFFFF) + ", saturation=" + (saturation & 0xFFFF) + ", brightness=" + (brightness & 0xFFFF) + ", kelvin=" + (kelvin & 0xFFFF) + ", duration=" + (duration & 0xFFFFFFFF) + ")";
    }
}
