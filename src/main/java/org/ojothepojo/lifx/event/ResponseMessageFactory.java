package org.ojothepojo.lifx.event;

import org.ojothepojo.lifx.message.Message;
import org.ojothepojo.lifx.message.device.request.GetPower;
import org.ojothepojo.lifx.message.device.request.GetService;
import org.ojothepojo.lifx.message.device.request.SetPower;
import org.ojothepojo.lifx.message.device.response.StatePower;
import org.ojothepojo.lifx.message.device.response.StateService;
import org.ojothepojo.lifx.message.light.request.GetColor;
import org.ojothepojo.lifx.message.light.request.SetColor;
import org.ojothepojo.lifx.message.light.response.StateColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ResponseMessageFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseMessageFactory.class);

    public static Message toMessage(byte[] bytes) {
        if (bytes.length < 36) {
            throw new IllegalArgumentException("No response package should be less then 36 bytes");
        }

        ByteBuffer buf = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
        buf.rewind();
        int type = buf.getShort(32) & 0xffff;
        Message message = null;
        switch (type) {
            case 2:
                message = new GetService(bytes);
                break;
            case 3:
                message = new StateService(bytes);
                break;
            case 20:
                message = new GetPower(bytes);
                break;
            case 21:
                message = new SetPower(bytes);
                break;
            case 22:
                message = new StatePower(bytes);
                break;
            case 101:
                message = new GetColor(bytes);
                break;
            case 102:
                message = new SetColor(bytes);
                break;
            case 107:
                message = new StateColor(bytes);
                break;

            default:
                LOGGER.warn("Not yet implemented: type " + type);
        }

        return message;
    }
}
