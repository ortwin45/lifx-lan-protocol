package org.ojothepojo.lifx.event;

import org.ojothepojo.lifx.message.Message;
import org.ojothepojo.lifx.message.request.GetService;
import org.ojothepojo.lifx.message.response.StateService;
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

            default:
                LOGGER.warn("Not yet implemented: type " + type);
        }

        return message;
    }
}
