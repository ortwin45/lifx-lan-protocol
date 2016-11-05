package org.ojothepojo.lifx.message.light.response;

import lombok.Getter;
import org.ojothepojo.lifx.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class StateColor extends Message {
    private static final Logger LOGGER = LoggerFactory.getLogger(StateColor.class);

    @Getter
    private int hue;
    @Getter
    private int saturation;
    @Getter
    private int brightness;
    @Getter
    private int kelvin;
    @Getter
    private boolean power;
    @Getter
    private String label;

    public StateColor(byte[] bytes) {
        super(bytes);
    }

    @Override
    public void parsePayload(ByteBuffer buf) {
        buf.rewind();
        buf = buf.order(ByteOrder.LITTLE_ENDIAN);
        hue = buf.getShort() & 0xffff;
        saturation = buf.getShort() & 0xffff;
        brightness = buf.getShort() & 0xffff;
        kelvin = buf.getShort() & 0xffff;
        buf.getShort(); // reserved
        short p = buf.getShort();
        power = p != 0;

        byte[] l = new byte[32];
        buf.get(l);
        int size = 0;
        while (size < l.length)
        {
            if (l[size] == 0)
            {
                break;
            }
            size++;
        }
        try {
            label = new String(l, 0, size, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    protected ByteBuffer payloadToBytes() {
        throw new RuntimeException("Shouldn't serialize a response message");
    }

    @Override
    public String toString() {
        return "StateColor" + super.toString() + "--payload(hue=" + hue + ", saturation=" + saturation+ ", brightness=" + brightness+ ", kelvin=" + kelvin+ ", power=" + power+ ", label="+ label + ")";
    }
}
