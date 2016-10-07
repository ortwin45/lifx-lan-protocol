package org.ojothepojo.lifx.message;

/**
 * 128 bits
 */
public class LifxFrameAddress {
    private byte[] input;

    public LifxFrameAddress(byte[] input) {
        this.input = input;
    }

    public byte[] getInput() {
        return input;
    }
}
