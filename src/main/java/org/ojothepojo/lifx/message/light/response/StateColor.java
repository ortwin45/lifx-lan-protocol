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
    private short hue;
    @Getter
    private short saturation;
    @Getter
    private short brightness;
    @Getter
    private short kelvin;
    @Getter
    private short power;
    @Getter
    private String label;

    public StateColor(byte[] bytes) {
        super(bytes);
    }

    @Override
    public void parsePayload(ByteBuffer buf) {
        buf.rewind();
        buf = buf.order(ByteOrder.LITTLE_ENDIAN);
        hue = buf.getShort();
        saturation = buf.getShort();
        brightness = buf.getShort();
        kelvin = buf.getShort();
        buf.getShort(); // reserved
        power = buf.getShort();

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
