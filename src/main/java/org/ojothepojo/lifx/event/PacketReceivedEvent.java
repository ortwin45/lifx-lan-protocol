package org.ojothepojo.lifx.event;


import lombok.Getter;

public class PacketReceivedEvent {

    @Getter
    private String message;

    public PacketReceivedEvent( ) {

    }

    public PacketReceivedEvent(String message) {
        this.message = message;
    }

}
