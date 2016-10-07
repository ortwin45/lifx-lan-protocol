package org.ojothepojo.lifx.message;


import java.util.Arrays;

public class LifxMessage {

    private static final int MESSAGE_MINIMAL_LENGHT = 8 + 16 + 12;

    private LifxFrame frame;

    private LifxFrameAddress frameAddress;

    private LifxHeader header;

    private LifxPayload payload;

    public LifxMessage(byte[] input) {
        if (input.length < MESSAGE_MINIMAL_LENGHT){
            throw new IllegalArgumentException("Message too short");
        }
        frame = new LifxFrame(Arrays.copyOfRange(input, 0, 8));
        frameAddress = new LifxFrameAddress(Arrays.copyOfRange(input, 8, 24));
        header = new LifxHeader(Arrays.copyOfRange(input, 24, 36));
        payload = new LifxPayload(Arrays.copyOfRange(input, 36, frame.getSize()));
    }

    public LifxFrame getFrame() {
        return frame;
    }


    public LifxFrameAddress getFrameAddress() {
        return frameAddress;
    }


    public LifxHeader getHeader() {
        return header;
    }


    public LifxPayload getPayload() {
        return payload;
    }

}
