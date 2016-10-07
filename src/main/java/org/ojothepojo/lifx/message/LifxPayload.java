package org.ojothepojo.lifx.message;

public class LifxPayload {

    private byte[] input;

    public LifxPayload(byte[] input) {
        this.input = input;
    }

    public byte[] getInput() {
        return input;
    }
}
